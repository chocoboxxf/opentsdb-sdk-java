package net.opentsdb.client.api.query.response;

import java.util.List;

import net.opentsdb.client.api.BaseResponse;
import net.opentsdb.client.bean.QueryResult;

/**
 * Query Response Object
 */
public class QueryResponse extends BaseResponse {

  /**
   * queried result list
   */
  private List<QueryResult> results;

  public QueryResponse() {
  }

  public List<QueryResult> getResults() {
    return results;
  }

  public void setResults(List<QueryResult> results) {
    this.results = results;
  }

  public static QueryResponseBuilder builder() {
    return new QueryResponseBuilder();
  }

  public static final class QueryResponseBuilder {

    private String requestUUID;
    private List<QueryResult> results;

    private QueryResponseBuilder() {
    }
    
    public QueryResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public QueryResponseBuilder results(List<QueryResult> results) {
      this.results = results;
      return this;
    }

    public QueryResponse build() {
      QueryResponse queryResponse = new QueryResponse();
      queryResponse.setRequestUUID(requestUUID);
      queryResponse.setResults(results);
      return queryResponse;
    }
  }
}
