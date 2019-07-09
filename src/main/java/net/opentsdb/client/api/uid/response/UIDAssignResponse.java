package net.opentsdb.client.api.uid.response;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentsdb.client.api.BaseResponse;

/**
 * UID Assign Response Object
 */
public class UIDAssignResponse extends BaseResponse {

  /**
   * successfully assigned metric names with their UIDs
   */
  @JsonProperty("metric")
  private Map<String, String> metric;

  /**
   * metric names which failed assignment with error messages
   */
  @JsonProperty("metric_errors")
  private Map<String, String> metricErrors;

  /**
   * successfully assigned tag names with their UIDs
   */
  @JsonProperty("tagk")
  private Map<String, String> tagk;

  /**
   * tag names which failed assignment with error messages
   */
  @JsonProperty("tagk_errors")
  private Map<String, String> tagkErrors;

  /**
   * successfully assigned tag values with their UIDs
   */
  @JsonProperty("tagv")
  private Map<String, String> tagv;

  /**
   * tag values which failed assignment with error messages
   */
  @JsonProperty("tagv_errors")
  private Map<String, String> tagvErrors;

  public UIDAssignResponse() {
  }

  public Map<String, String> getMetric() {
    return metric;
  }

  public void setMetric(Map<String, String> metric) {
    this.metric = metric;
  }

  public Map<String, String> getMetricErrors() {
    return metricErrors;
  }

  public void setMetricErrors(Map<String, String> metricErrors) {
    this.metricErrors = metricErrors;
  }

  public Map<String, String> getTagk() {
    return tagk;
  }

  public void setTagk(Map<String, String> tagk) {
    this.tagk = tagk;
  }

  public Map<String, String> getTagkErrors() {
    return tagkErrors;
  }

  public void setTagkErrors(Map<String, String> tagkErrors) {
    this.tagkErrors = tagkErrors;
  }

  public Map<String, String> getTagv() {
    return tagv;
  }

  public void setTagv(Map<String, String> tagv) {
    this.tagv = tagv;
  }

  public Map<String, String> getTagvErrors() {
    return tagvErrors;
  }

  public void setTagvErrors(Map<String, String> tagvErrors) {
    this.tagvErrors = tagvErrors;
  }

  public static UIDAssignResponseBuilder builder() {
    return new UIDAssignResponseBuilder();
  }
  
  public static final class UIDAssignResponseBuilder {

    private String requestUUID;
    private Map<String, String> metric;
    private Map<String, String> metricErrors;
    private Map<String, String> tagk;
    private Map<String, String> tagkErrors;
    private Map<String, String> tagv;
    private Map<String, String> tagvErrors;

    private UIDAssignResponseBuilder() {
    }

    public UIDAssignResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public UIDAssignResponseBuilder metric(Map<String, String> metric) {
      this.metric = metric;
      return this;
    }

    public UIDAssignResponseBuilder metricErrors(Map<String, String> metricErrors) {
      this.metricErrors = metricErrors;
      return this;
    }

    public UIDAssignResponseBuilder tagk(Map<String, String> tagk) {
      this.tagk = tagk;
      return this;
    }

    public UIDAssignResponseBuilder tagkErrors(Map<String, String> tagkErrors) {
      this.tagkErrors = tagkErrors;
      return this;
    }

    public UIDAssignResponseBuilder tagv(Map<String, String> tagv) {
      this.tagv = tagv;
      return this;
    }

    public UIDAssignResponseBuilder tagvErrors(Map<String, String> tagvErrors) {
      this.tagvErrors = tagvErrors;
      return this;
    }

    public UIDAssignResponse build() {
      UIDAssignResponse uIDAssignResponse = new UIDAssignResponse();
      uIDAssignResponse.setRequestUUID(requestUUID);
      uIDAssignResponse.setMetric(metric);
      uIDAssignResponse.setMetricErrors(metricErrors);
      uIDAssignResponse.setTagk(tagk);
      uIDAssignResponse.setTagkErrors(tagkErrors);
      uIDAssignResponse.setTagv(tagv);
      uIDAssignResponse.setTagvErrors(tagvErrors);
      return uIDAssignResponse;
    }
  }
}
