package net.opentsdb.client.bean;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LastDataPoint extends BaseBean {

  /**
   * Name of the metric for the time series. Only returned if {@code resolve} was set to true.
   * <br/>Example: sys.cpu.nice
   */
  @JsonProperty("metric")
  private String metric;

  /**
   * A list of tags for the time series. Only returned if {@code resolve} was set to true.
   * <br/>Example: {"host":"web01"}
   */
  @JsonProperty("tags")
  private Map<String, String> tags;

  /**
   * A Unix epoch timestamp, in milliseconds, when the data point was written
   * <br/>Example: 1377118201000
   */
  @JsonProperty("timestamp")
  private Long timestamp;

  /**
   * The value of the data point enclosed in quotation marks as a string
   * <br/>Example: 42.5
   */
  @JsonProperty("value")
  private String value;

  /**
   * The hexadecimal TSUID for the time series
   * <br/>Example: 0023E3000002000008000006001656
   */
  @JsonProperty("tsuid")
  private String tsuid;

  public LastDataPoint() {
  }

  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getTsuid() {
    return tsuid;
  }

  public void setTsuid(String tsuid) {
    this.tsuid = tsuid;
  }

  public static LastDataPointBuilder builder() {
    return new LastDataPointBuilder();
  }

  public static final class LastDataPointBuilder {

    private String metric;
    private Map<String, String> tags;
    private Long timestamp;
    private String value;
    private String tsuid;

    private LastDataPointBuilder() {
    }

    public LastDataPointBuilder metric(String metric) {
      this.metric = metric;
      return this;
    }

    public LastDataPointBuilder tags(Map<String, String> tags) {
      this.tags = tags;
      return this;
    }

    public LastDataPointBuilder timestamp(Long timestamp) {
      this.timestamp = timestamp;
      return this;
    }

    public LastDataPointBuilder value(String value) {
      this.value = value;
      return this;
    }

    public LastDataPointBuilder tsuid(String tsuid) {
      this.tsuid = tsuid;
      return this;
    }

    public LastDataPoint build() {
      LastDataPoint lastDataPoint = new LastDataPoint();
      lastDataPoint.setMetric(metric);
      lastDataPoint.setTags(tags);
      lastDataPoint.setTimestamp(timestamp);
      lastDataPoint.setValue(value);
      lastDataPoint.setTsuid(tsuid);
      return lastDataPoint;
    }
  }
}
