package net.opentsdb.client.api.put.callback;

import java.io.IOException;
import java.util.LinkedList;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.put.request.PutRequest;
import net.opentsdb.client.api.put.response.PutResponse;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;

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
      PutResponse result = HttpUtil.getResponse(response, PutResponse.class);
      // empty response
      if (result == null) {
        result = PutResponse.builder()
            .success(request.getDataPoints().size())
            .failed(0)
            .errors(new LinkedList<>())
            .build();
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
