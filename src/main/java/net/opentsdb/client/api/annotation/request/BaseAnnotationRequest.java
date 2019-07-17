package net.opentsdb.client.api.annotation.request;

import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentsdb.client.api.BaseRequest;

/**
 * Base Annotation Request Object
 */
public abstract class BaseAnnotationRequest extends BaseRequest {

  /**
   * A Unix epoch timestamp, in seconds,
   * marking the time when the annotation event should be recorded
   * <br/>Example: 1369141261
   */
  @JsonProperty(value = "startTime", required = true)
  private Integer startTime;

  /**
   * An optional end time for the event if it has completed or been resolved
   * <br/>Default: 0
   * <br/>Example: 1369141262
   */
  @JsonProperty("endTime")
  private Integer endTime;

  /**
   * A TSUID if the annotation is associated with a timeseries.
   * This may be null or empty if the note was for a global event
   * <br/>Example: 000001000001000001
   */
  @JsonProperty("tsuid")
  private String tsuid;

  /**
   * A brief description of the event. As this may appear on GnuPlot graphs,
   * the description should be very short, ideally less than 25 characters.
   * <br/>Example: Network Outage
   */
  @JsonProperty("description")
  private String description;

  /**
   * Detailed notes about the event
   * <br/>Example: Switch #5 died and was replaced
   */
  @JsonProperty("notes")
  private String notes;

  /**
   * A key/value map to store custom fields and values
   * <br/>Default: null
   * <br/>Example: {"owner": "jdoe", "dept": "ops"}
   */
  @JsonProperty("custom")
  private Map<String, String> custom;

  public BaseAnnotationRequest() {
  }

  public Integer getStartTime() {
    return startTime;
  }

  public void setStartTime(Integer startTime) {
    this.startTime = startTime;
  }

  public Integer getEndTime() {
    return endTime;
  }

  public void setEndTime(Integer endTime) {
    this.endTime = endTime;
  }

  public String getTsuid() {
    return tsuid;
  }

  public void setTsuid(String tsuid) {
    this.tsuid = tsuid;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public Map<String, String> getCustom() {
    return custom;
  }

  public void setCustom(Map<String, String> custom) {
    this.custom = custom;
  }

  public Map<String, String> getParameters() {
    Map<String, String> parameters = new LinkedHashMap<>();
    if (startTime != null) {
      parameters.put("start_time", startTime.toString());
    }
    if (endTime != null) {
      parameters.put("end_time", endTime.toString());
    }
    if (tsuid != null) {
      parameters.put("tsuid", tsuid);
    }
    if (description != null) {
      parameters.put("description", description);
    }
    if (notes != null) {
      parameters.put("notes", notes);
    }
    return parameters;
  }
}
