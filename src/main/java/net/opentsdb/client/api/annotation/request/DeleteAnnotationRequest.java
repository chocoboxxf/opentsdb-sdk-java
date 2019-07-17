package net.opentsdb.client.api.annotation.request;

/**
 * Delete Annotation Request Object
 */
public class DeleteAnnotationRequest extends BaseAnnotationRequest {

  public DeleteAnnotationRequest() {
  }

  public static DeleteAnnotationRequestBuilder builder() {
    return new DeleteAnnotationRequestBuilder();
  }

  public static final class DeleteAnnotationRequestBuilder {

    private String requestUUID;
    private Integer startTime;
    private Integer endTime;
    private String tsuid;

    private DeleteAnnotationRequestBuilder() {
    }

    public DeleteAnnotationRequestBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public DeleteAnnotationRequestBuilder startTime(Integer startTime) {
      this.startTime = startTime;
      return this;
    }

    public DeleteAnnotationRequestBuilder endTime(Integer endTime) {
      this.endTime = endTime;
      return this;
    }

    public DeleteAnnotationRequestBuilder tsuid(String tsuid) {
      this.tsuid = tsuid;
      return this;
    }

    public DeleteAnnotationRequest build() {
      DeleteAnnotationRequest deleteAnnotationRequest = new DeleteAnnotationRequest();
      if (requestUUID != null) {
        deleteAnnotationRequest.setRequestUUID(requestUUID);
      }
      deleteAnnotationRequest.setStartTime(startTime);
      deleteAnnotationRequest.setEndTime(endTime);
      deleteAnnotationRequest.setTsuid(tsuid);
      return deleteAnnotationRequest;
    }
  }
}
