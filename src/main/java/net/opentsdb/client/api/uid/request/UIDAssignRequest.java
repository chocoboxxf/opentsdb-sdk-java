package net.opentsdb.client.api.uid.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentsdb.client.api.BaseRequest;

/**
 * UID Assign Request Object
 */
public class UIDAssignRequest extends BaseRequest {

  /**
   * A list of metric names for assignment
   */
  @JsonProperty("metric")
  private List<String> metric;

  /**
   * A list of tag names for assignment
   */
  @JsonProperty("tagk")
  private List<String> tagk;

  /**
   * A list of tag values for assignment
   */
  @JsonProperty("tagv")
  private List<String> tagv;

  public UIDAssignRequest() {
  }

  public List<String> getMetric() {
    return metric;
  }

  public void setMetric(List<String> metric) {
    this.metric = metric;
  }

  public List<String> getTagk() {
    return tagk;
  }

  public void setTagk(List<String> tagk) {
    this.tagk = tagk;
  }

  public List<String> getTagv() {
    return tagv;
  }

  public void setTagv(List<String> tagv) {
    this.tagv = tagv;
  }

  public static UIDAssignRequestBuilder builder() {
    return new UIDAssignRequestBuilder();
  }

  public static final class UIDAssignRequestBuilder {

    List<String> metric;
    List<String> tagk;
    List<String> tagv;
    private String requestUUID;

    private UIDAssignRequestBuilder() {
    }

    public UIDAssignRequestBuilder metric(List<String> metric) {
      this.metric = metric;
      return this;
    }

    public UIDAssignRequestBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public UIDAssignRequestBuilder tagk(List<String> tagk) {
      this.tagk = tagk;
      return this;
    }

    public UIDAssignRequestBuilder tagv(List<String> tagv) {
      this.tagv = tagv;
      return this;
    }

    public UIDAssignRequest build() {
      UIDAssignRequest uIDAssignRequest = new UIDAssignRequest();
      if (requestUUID != null) {
        uIDAssignRequest.setRequestUUID(requestUUID);
      }
      uIDAssignRequest.setMetric(metric);
      uIDAssignRequest.setTagk(tagk);
      uIDAssignRequest.setTagv(tagv);
      return uIDAssignRequest;
    }
  }
}
