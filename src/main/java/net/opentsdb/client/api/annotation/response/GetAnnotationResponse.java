package net.opentsdb.client.api.annotation.response;

import net.opentsdb.client.bean.Annotation;

/**
 * Get Annotation Response Object
 */
public class GetAnnotationResponse extends BaseAnnotationResponse {

  public GetAnnotationResponse() {
  }

  public static GetAnnotationResponseBuilder builder() {
    return new GetAnnotationResponseBuilder();
  }

  public static final class GetAnnotationResponseBuilder {

    private Annotation result;
    private String requestUUID;

    private GetAnnotationResponseBuilder() {
    }

    public GetAnnotationResponseBuilder result(Annotation result) {
      this.result = result;
      return this;
    }

    public GetAnnotationResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public GetAnnotationResponse build() {
      GetAnnotationResponse getAnnotationResponse = new GetAnnotationResponse();
      getAnnotationResponse.setResult(result);
      getAnnotationResponse.setRequestUUID(requestUUID);
      return getAnnotationResponse;
    }
  }
}
