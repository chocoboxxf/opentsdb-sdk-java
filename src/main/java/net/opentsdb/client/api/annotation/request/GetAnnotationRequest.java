package net.opentsdb.client.api.annotation.request;

/**
 * Get Annotation Request Object
 */
public class GetAnnotationRequest extends BaseAnnotationRequest {

  public GetAnnotationRequest() {
  }

  public static GetAnnotationRequestBuilder builder() {
    return new GetAnnotationRequestBuilder();
  }

  public static final class GetAnnotationRequestBuilder {

    private String requestUUID;
    private Integer startTime;
    private Integer endTime;
    private String tsuid;

    private GetAnnotationRequestBuilder() {
    }

    public GetAnnotationRequestBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public GetAnnotationRequestBuilder startTime(Integer startTime) {
      this.startTime = startTime;
      return this;
    }

    public GetAnnotationRequestBuilder endTime(Integer endTime) {
      this.endTime = endTime;
      return this;
    }

    public GetAnnotationRequestBuilder tsuid(String tsuid) {
      this.tsuid = tsuid;
      return this;
    }

    public GetAnnotationRequest build() {
      GetAnnotationRequest getAnnotationRequest = new GetAnnotationRequest();
      if (requestUUID != null) {
        getAnnotationRequest.setRequestUUID(requestUUID);
      }
      getAnnotationRequest.setStartTime(startTime);
      getAnnotationRequest.setEndTime(endTime);
      getAnnotationRequest.setTsuid(tsuid);
      return getAnnotationRequest;
    }
  }
}
