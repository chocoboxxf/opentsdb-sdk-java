package net.opentsdb.client.api.annotation.response;

import net.opentsdb.client.api.BaseResponse;

/**
 * Delete Annotation Response Object
 */
public class DeleteAnnotationResponse extends BaseResponse {

  public DeleteAnnotationResponse() {
  }

  public static DeleteAnnotationResponseBuilder builder() {
    return new DeleteAnnotationResponseBuilder();
  }

  public static final class DeleteAnnotationResponseBuilder {

    private String requestUUID;

    private DeleteAnnotationResponseBuilder() {
    }

    public DeleteAnnotationResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public DeleteAnnotationResponse build() {
      DeleteAnnotationResponse deleteAnnotationResponse = new DeleteAnnotationResponse();
      deleteAnnotationResponse.setRequestUUID(requestUUID);
      return deleteAnnotationResponse;
    }
  }
}
