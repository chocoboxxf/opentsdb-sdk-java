package net.opentsdb.client.api.annotation.response;

import net.opentsdb.client.bean.Annotation;

/**
 * Update Annotation Response Object
 */
public class UpdateAnnotationResponse extends BaseAnnotationResponse {

  public UpdateAnnotationResponse() {
  }

  public static UpdateAnnotationResponseBuilder builder() {
    return new UpdateAnnotationResponseBuilder();
  }

  public static final class UpdateAnnotationResponseBuilder {

    private Annotation result;
    private String requestUUID;

    private UpdateAnnotationResponseBuilder() {
    }

    public UpdateAnnotationResponseBuilder result(Annotation result) {
      this.result = result;
      return this;
    }

    public UpdateAnnotationResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public UpdateAnnotationResponse build() {
      UpdateAnnotationResponse updateAnnotationResponse = new UpdateAnnotationResponse();
      updateAnnotationResponse.setResult(result);
      updateAnnotationResponse.setRequestUUID(requestUUID);
      return updateAnnotationResponse;
    }
  }
}
