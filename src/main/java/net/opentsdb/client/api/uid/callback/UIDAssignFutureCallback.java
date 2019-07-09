package net.opentsdb.client.api.uid.callback;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.uid.request.UIDAssignRequest;
import net.opentsdb.client.api.uid.response.UIDAssignResponse;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;
import net.opentsdb.client.util.JsonUtil;

/**
 * FutureCallback for UID Assign API
 */
public class UIDAssignFutureCallback implements FutureCallback<HttpResponse> {

  /**
   * Request Object
   */
  private UIDAssignRequest request;

  /**
   * Callback Object
   */
  private UIDAssignCallback callback;

  public UIDAssignFutureCallback(UIDAssignRequest request,
      UIDAssignCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      // return 400 when error items exists
      UIDAssignResponse result = JsonUtil
          .readValue(HttpUtil.getContent(response, true), UIDAssignResponse.class);
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
