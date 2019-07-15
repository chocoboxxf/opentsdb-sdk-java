package net.opentsdb.client.api.query.response;

import java.util.List;

import net.opentsdb.client.api.BaseResponse;
import net.opentsdb.client.bean.LastDataPoint;

/**
 * Query Latest Data Response Object
 */
public class QueryLastResponse extends BaseResponse {

  /**
   * queried result list
   */
  private List<LastDataPoint> results;

  public QueryLastResponse() {
  }

  public List<LastDataPoint> getResults() {
    return results;
  }

  public void setResults(List<LastDataPoint> results) {
    this.results = results;
  }

  public static QueryLastResponseBuilder builder() {
    return new QueryLastResponseBuilder();
  }

  public static final class QueryLastResponseBuilder {

    private List<LastDataPoint> results;
    private String requestUUID;

    private QueryLastResponseBuilder() {
    }

    public QueryLastResponseBuilder results(List<LastDataPoint> results) {
      this.results = results;
      return this;
    }

    public QueryLastResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public QueryLastResponse build() {
      QueryLastResponse queryLastResponse = new QueryLastResponse();
      queryLastResponse.setResults(results);
      queryLastResponse.setRequestUUID(requestUUID);
      return queryLastResponse;
    }
  }
}
