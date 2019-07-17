package net.opentsdb.client.api.annotation.response;

import net.opentsdb.client.bean.Annotation;

/**
 * Create Annotation Response Object
 */
public class CreateAnnotationResponse extends BaseAnnotationResponse {

  public CreateAnnotationResponse() {
  }

  public static CreateAnnotationResponseBuilder builder() {
    return new CreateAnnotationResponseBuilder();
  }

  public static final class CreateAnnotationResponseBuilder {

    private Annotation result;
    private String requestUUID;

    private CreateAnnotationResponseBuilder() {
    }

    public CreateAnnotationResponseBuilder result(Annotation result) {
      this.result = result;
      return this;
    }

    public CreateAnnotationResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public CreateAnnotationResponse build() {
      CreateAnnotationResponse createAnnotationResponse = new CreateAnnotationResponse();
      createAnnotationResponse.setResult(result);
      createAnnotationResponse.setRequestUUID(requestUUID);
      return createAnnotationResponse;
    }
  }
}
