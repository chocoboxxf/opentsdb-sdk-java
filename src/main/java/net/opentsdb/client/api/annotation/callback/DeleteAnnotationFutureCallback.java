package net.opentsdb.client.api.annotation.callback;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.annotation.request.DeleteAnnotationRequest;
import net.opentsdb.client.api.annotation.response.DeleteAnnotationResponse;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;

/**
 * FutureCallback for Delete Annotation API
 */
public class DeleteAnnotationFutureCallback implements FutureCallback<HttpResponse> {

  /**
   * Request Object
   */
  private DeleteAnnotationRequest request;

  /**
   * Callback Object
   */
  private DeleteAnnotationCallback callback;

  public DeleteAnnotationFutureCallback(
      DeleteAnnotationRequest request,
      DeleteAnnotationCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      DeleteAnnotationResponse result = HttpUtil
          .getResponse(response, DeleteAnnotationResponse.class);
      // empty response
      if (result == null) {
        result = DeleteAnnotationResponse.builder()
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
