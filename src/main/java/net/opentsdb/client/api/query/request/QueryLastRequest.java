package net.opentsdb.client.api.query.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentsdb.client.api.BaseRequest;
import net.opentsdb.client.bean.LastDataPointQuery;

/**
 * Query Latest Data Request Object
 */
public class QueryLastRequest extends BaseRequest {

  /**
   * A list of one or more queries used to determine
   * which time series to fetch the last data point for.
   */
  @JsonProperty(value = "queries", required = true)
  private List<LastDataPointQuery> queries;

  /**
   * Whether or not to resolve the TSUIDs of results to their metric and tag names.
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("resolveNames")
  private Boolean resolveNames;

  /**
   * A number of hours to search in the past for data.
   * If set to 0 then the timestamp of the meta data counter for the time series is used.
   * <br/>Default: 0
   * <br/>Example: 24
   */
  @JsonProperty("backScan")
  private Integer backScan;

  public QueryLastRequest() {
  }

  public List<LastDataPointQuery> getQueries() {
    return queries;
  }

  public void setQueries(List<LastDataPointQuery> queries) {
    this.queries = queries;
  }

  public Boolean getResolveNames() {
    return resolveNames;
  }

  public void setResolveNames(Boolean resolveNames) {
    this.resolveNames = resolveNames;
  }

  public Integer getBackScan() {
    return backScan;
  }

  public void setBackScan(Integer backScan) {
    this.backScan = backScan;
  }

  public static QueryLastRequestBuilder builder() {
    return new QueryLastRequestBuilder();
  }

  public static final class QueryLastRequestBuilder {

    private List<LastDataPointQuery> queries;
    private String requestUUID;
    private Boolean resolveNames;
    private Integer backScan;

    private QueryLastRequestBuilder() {
    }

    public QueryLastRequestBuilder queries(List<LastDataPointQuery> queries) {
      this.queries = queries;
      return this;
    }

    public QueryLastRequestBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public QueryLastRequestBuilder resolveNames(Boolean resolveNames) {
      this.resolveNames = resolveNames;
      return this;
    }

    public QueryLastRequestBuilder backScan(Integer backScan) {
      this.backScan = backScan;
      return this;
    }

    public QueryLastRequest build() {
      QueryLastRequest queryLastRequest = new QueryLastRequest();
      if (requestUUID != null) {
        queryLastRequest.setRequestUUID(requestUUID);
      }
      queryLastRequest.setQueries(queries);
      queryLastRequest.setResolveNames(resolveNames);
      queryLastRequest.setBackScan(backScan);
      return queryLastRequest;
    }
  }
}
