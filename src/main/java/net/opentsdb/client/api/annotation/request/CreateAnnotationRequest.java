package net.opentsdb.client.api.annotation.request;

import java.util.Map;

/**
 * Create Annotation Request Object
 */
public class CreateAnnotationRequest extends BaseAnnotationRequest {

  public CreateAnnotationRequest() {
  }

  public static CreateAnnotationRequestBuilder builder() {
    return new CreateAnnotationRequestBuilder();
  }

  public static final class CreateAnnotationRequestBuilder {

    private String requestUUID;
    private Integer startTime;
    private Integer endTime;
    private String tsuid;
    private String description;
    private String notes;
    private Map<String, String> custom;

    private CreateAnnotationRequestBuilder() {
    }

    public CreateAnnotationRequestBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public CreateAnnotationRequestBuilder startTime(Integer startTime) {
      this.startTime = startTime;
      return this;
    }

    public CreateAnnotationRequestBuilder endTime(Integer endTime) {
      this.endTime = endTime;
      return this;
    }

    public CreateAnnotationRequestBuilder tsuid(String tsuid) {
      this.tsuid = tsuid;
      return this;
    }

    public CreateAnnotationRequestBuilder description(String description) {
      this.description = description;
      return this;
    }

    public CreateAnnotationRequestBuilder notes(String notes) {
      this.notes = notes;
      return this;
    }

    public CreateAnnotationRequestBuilder custom(Map<String, String> custom) {
      this.custom = custom;
      return this;
    }

    public CreateAnnotationRequest build() {
      CreateAnnotationRequest createAnnotationRequest = new CreateAnnotationRequest();
      if (requestUUID != null) {
        createAnnotationRequest.setRequestUUID(requestUUID);
      }
      createAnnotationRequest.setStartTime(startTime);
      createAnnotationRequest.setEndTime(endTime);
      createAnnotationRequest.setTsuid(tsuid);
      createAnnotationRequest.setDescription(description);
      createAnnotationRequest.setNotes(notes);
      createAnnotationRequest.setCustom(custom);
      return createAnnotationRequest;
    }
  }
}
