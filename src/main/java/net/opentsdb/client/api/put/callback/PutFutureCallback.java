package net.opentsdb.client.api.put.callback;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.put.request.PutRequest;
import net.opentsdb.client.api.put.response.PutResponse;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;
import net.opentsdb.client.util.JsonUtil;

/**
 * FutureCallback for Put API
 */
public class PutFutureCallback implements FutureCallback<HttpResponse> {
  
  /**
   * Request Object
   */
  private PutRequest request;

  /**
   * Callback Object
   */
  private PutCallback callback;

  public PutFutureCallback(PutRequest request,
      PutCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      PutResponse result = JsonUtil.readValue(HttpUtil.getContent(response), PutResponse.class);
      if (result == null && response.getStatusLine().getStatusCode() == HttpStatus.SC_NO_CONTENT) {
        result = new PutResponse();
        result.setSuccess(request.getDataPoints().size());
        result.setFailed(0);
        result.setErrors(new LinkedList<>());
      }
      result.setRequestUUID(request.getRequestUUID());
      callback.response(result);
    } catch (ErrorException ee) {
      this.callback.responseError(ee);
    } catch (IOException e) {
      e.printStackTrace();
      this.callback.failed(e);
    }
  }

  @Override
  public void failed(Exception e) {
    e.printStackTrace();
    this.callback.failed(e);
  }

  @Override
  public void cancelled() {
    // Not implemented
  }
}
