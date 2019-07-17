package net.opentsdb.client.api.annotation.request;

import java.util.Map;

/**
 * Update Annotation Request Object
 */
public class UpdateAnnotationRequest extends BaseAnnotationRequest {

  public UpdateAnnotationRequest() {
  }

  public static UpdateAnnotationRequestBuilder builder() {
    return new UpdateAnnotationRequestBuilder();
  }

  public static final class UpdateAnnotationRequestBuilder {

    private String requestUUID;
    private Integer startTime;
    private Integer endTime;
    private String tsuid;
    private String description;
    private String notes;
    private Map<String, String> custom;

    private UpdateAnnotationRequestBuilder() {
    }

    public UpdateAnnotationRequestBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public UpdateAnnotationRequestBuilder startTime(Integer startTime) {
      this.startTime = startTime;
      return this;
    }

    public UpdateAnnotationRequestBuilder endTime(Integer endTime) {
      this.endTime = endTime;
      return this;
    }

    public UpdateAnnotationRequestBuilder tsuid(String tsuid) {
      this.tsuid = tsuid;
      return this;
    }

    public UpdateAnnotationRequestBuilder description(String description) {
      this.description = description;
      return this;
    }

    public UpdateAnnotationRequestBuilder notes(String notes) {
      this.notes = notes;
      return this;
    }

    public UpdateAnnotationRequestBuilder custom(Map<String, String> custom) {
      this.custom = custom;
      return this;
    }

    public UpdateAnnotationRequest build() {
      UpdateAnnotationRequest updateAnnotationRequest = new UpdateAnnotationRequest();
      if (requestUUID != null) {
        updateAnnotationRequest.setRequestUUID(requestUUID);
      }
      updateAnnotationRequest.setStartTime(startTime);
      updateAnnotationRequest.setEndTime(endTime);
      updateAnnotationRequest.setTsuid(tsuid);
      updateAnnotationRequest.setDescription(description);
      updateAnnotationRequest.setNotes(notes);
      updateAnnotationRequest.setCustom(custom);
      return updateAnnotationRequest;
    }
  }
}
