package net.opentsdb.client.api.annotation.callback;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.annotation.request.GetAnnotationRequest;
import net.opentsdb.client.api.annotation.response.GetAnnotationResponse;
import net.opentsdb.client.bean.Annotation;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;

/**
 * FutureCallback for Get Annotation API
 */
public class GetAnnotationFutureCallback implements FutureCallback<HttpResponse> {

  /**
   * Request Object
   */
  private GetAnnotationRequest request;

  /**
   * Callback Object
   */
  private GetAnnotationCallback callback;

  public GetAnnotationFutureCallback(
      GetAnnotationRequest request,
      GetAnnotationCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      Annotation result = HttpUtil.getResponse(response, Annotation.class);
      GetAnnotationResponse getAnnotationResponse = GetAnnotationResponse.builder()
          .requestUUID(request.getRequestUUID())
          .result(result)
          .build();
      callback.response(getAnnotationResponse);
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
