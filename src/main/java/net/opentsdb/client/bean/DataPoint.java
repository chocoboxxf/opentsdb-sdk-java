package net.opentsdb.client.bean;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Data Point objects
 */
public class DataPoint extends BaseBean {

  /**
   * The name of the metric you are storing
   * <br/>Example: sys.cpu.nice
   */
  @JsonProperty(value = "metric", required = true)
  private String metric;

  /**
   * A Unix epoch style timestamp in seconds or milliseconds.
   * The timestamp must not contain non-numeric characters.
   * <br/>Example: 1365465600
   */
  @JsonProperty(value = "timestamp", required = true)
  private Long timestamp;

  /**
   * The value to record for this data point.
   * It may be quoted or not quoted and must conform to the OpenTSDB value rules
   * <br/>Example: 42.5
   * @see <a href="http://opentsdb.net/docs/build/html/user_guide/writing">Writing Data</a>
   */
  @JsonProperty(value = "value", required = true)
  private Number value;

  /**
   * A map of tag name/tag value pairs. At least one pair must be supplied.
   * <br/>Example: {"host":"web01"}
   */
  @JsonProperty(value = "tags", required = true)
  private Map<String, String> tags;

  public DataPoint() {
  }

  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public Number getValue() {
    return value;
  }

  public void setValue(Number value) {
    this.value = value;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  public static DataPointBuilder builder() {
    return new DataPointBuilder();
  }

  public static final class DataPointBuilder {

    private String metric;
    private Long timestamp;
    private Number value;
    private Map<String, String> tags;

    private DataPointBuilder() {
    }

    public DataPointBuilder metric(String metric) {
      this.metric = metric;
      return this;
    }

    public DataPointBuilder timestamp(Long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public DataPointBuilder value(Number value) {
      this.value = value;
      return this;
    }

    public DataPointBuilder tags(Map<String, String> tags) {
      this.tags = tags;
      return this;
    }

    public DataPoint build() {
      DataPoint dataPoint = new DataPoint();
      dataPoint.setMetric(metric);
      dataPoint.setTimestamp(timestamp);
      dataPoint.setValue(value);
      dataPoint.setTags(tags);
      return dataPoint;
    }
  }
}
