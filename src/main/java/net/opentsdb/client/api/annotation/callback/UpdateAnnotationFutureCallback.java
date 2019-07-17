package net.opentsdb.client.api.annotation.callback;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.annotation.request.UpdateAnnotationRequest;
import net.opentsdb.client.api.annotation.response.UpdateAnnotationResponse;
import net.opentsdb.client.bean.Annotation;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;

/**
 * FutureCallback for Update Annotation API
 */
public class UpdateAnnotationFutureCallback implements FutureCallback<HttpResponse> {

  /**
   * Request Object
   */
  private UpdateAnnotationRequest request;

  /**
   * Callback Object
   */
  private UpdateAnnotationCallback callback;

  public UpdateAnnotationFutureCallback(
      UpdateAnnotationRequest request,
      UpdateAnnotationCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      Annotation result = HttpUtil.getResponse(response, Annotation.class);
      UpdateAnnotationResponse updateAnnotationResponse = UpdateAnnotationResponse.builder()
          .requestUUID(request.getRequestUUID())
          .result(result)
          .build();
      callback.response(updateAnnotationResponse);
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
