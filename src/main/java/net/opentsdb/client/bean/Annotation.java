package net.opentsdb.client.bean;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Annotation objects
 */
public class Annotation extends BaseBean {

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

  public Annotation() {
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

  public static AnnotationBuilder builder() {
    return new AnnotationBuilder();
  }
  
  public static final class AnnotationBuilder {

    private Integer startTime;
    private Integer endTime;
    private String tsuid;
    private String description;
    private String notes;
    private Map<String, String> custom;

    private AnnotationBuilder() {
    }

    public AnnotationBuilder startTime(Integer startTime) {
      this.startTime = startTime;
      return this;
    }

    public AnnotationBuilder endTime(Integer endTime) {
      this.endTime = endTime;
      return this;
    }

    public AnnotationBuilder tsuid(String tsuid) {
      this.tsuid = tsuid;
      return this;
    }

    public AnnotationBuilder description(String description) {
      this.description = description;
      return this;
    }

    public AnnotationBuilder notes(String notes) {
      this.notes = notes;
      return this;
    }

    public AnnotationBuilder custom(Map<String, String> custom) {
      this.custom = custom;
      return this;
    }

    public Annotation build() {
      Annotation annotation = new Annotation();
      annotation.setStartTime(startTime);
      annotation.setEndTime(endTime);
      annotation.setTsuid(tsuid);
      annotation.setDescription(description);
      annotation.setNotes(notes);
      annotation.setCustom(custom);
      return annotation;
    }
  }
}
