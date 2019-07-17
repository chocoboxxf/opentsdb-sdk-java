package net.opentsdb.client.api.annotation.callback;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.annotation.request.CreateAnnotationRequest;
import net.opentsdb.client.api.annotation.response.CreateAnnotationResponse;
import net.opentsdb.client.bean.Annotation;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;

/**
 * FutureCallback for Create Annotation API
 */
public class CreateAnnotationFutureCallback implements FutureCallback<HttpResponse> {

  /**
   * Request Object
   */
  private CreateAnnotationRequest request;

  /**
   * Callback Object
   */
  private CreateAnnotationCallback callback;

  public CreateAnnotationFutureCallback(
      CreateAnnotationRequest request,
      CreateAnnotationCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      Annotation result = HttpUtil.getResponse(response, Annotation.class);
      CreateAnnotationResponse createAnnotationResponse = CreateAnnotationResponse.builder()
          .requestUUID(request.getRequestUUID())
          .result(result)
          .build();
      callback.response(createAnnotationResponse);
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
