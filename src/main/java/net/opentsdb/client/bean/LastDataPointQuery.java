package net.opentsdb.client.bean;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LastDataPointQuery extends BaseBean {

  /**
   * The name of a metric stored in the system
   * <br/>Example: sys.cpu.0
   */
  @JsonProperty(value = "metric")
  private String metric;

  /**
   * To drill down to specific timeseries or group results by tag
   * <br/>Example: {"host":"web01"}
   */
  @JsonProperty("tags")
  private Map<String, String> tags;

  /**
   * TSUIDs associated with timeseries
   * <br/>Example: ["0023E3000002000008000006001656"]
   */
  @JsonProperty("tsuids")
  private List<String> tsuids;

  public LastDataPointQuery() {
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

  public List<String> getTsuids() {
    return tsuids;
  }

  public void setTsuids(List<String> tsuids) {
    this.tsuids = tsuids;
  }

  public static LastDataPointQueryBuilder builder() {
    return new LastDataPointQueryBuilder();
  }

  public static final class LastDataPointQueryBuilder {

    private String metric;
    private Map<String, String> tags;
    private List<String> tsuids;

    private LastDataPointQueryBuilder() {
    }

    public LastDataPointQueryBuilder metric(String metric) {
      this.metric = metric;
      return this;
    }

    public LastDataPointQueryBuilder tags(Map<String, String> tags) {
      this.tags = tags;
      return this;
    }

    public LastDataPointQueryBuilder tsuids(List<String> tsuids) {
      this.tsuids = tsuids;
      return this;
    }

    public LastDataPointQuery build() {
      LastDataPointQuery lastDataPointQuery = new LastDataPointQuery();
      lastDataPointQuery.setMetric(metric);
      lastDataPointQuery.setTags(tags);
      lastDataPointQuery.setTsuids(tsuids);
      return lastDataPointQuery;
    }
  }
}
