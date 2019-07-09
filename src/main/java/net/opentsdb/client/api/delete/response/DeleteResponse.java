package net.opentsdb.client.api.delete.response;

import java.util.List;

import net.opentsdb.client.api.BaseResponse;
import net.opentsdb.client.bean.QueryResult;

/**
 * Delete Response Object (based on Query Response)
 */
public class DeleteResponse extends BaseResponse {

  /**
   * deleted result list
   */
  private List<QueryResult> results;

  public DeleteResponse() {
  }

  public List<QueryResult> getResults() {
    return results;
  }

  public void setResults(List<QueryResult> results) {
    this.results = results;
  }

  public static DeleteResponseBuilder builder() {
    return new DeleteResponseBuilder();
  }

  public static final class DeleteResponseBuilder {

    private List<QueryResult> results;
    private String requestUUID;

    private DeleteResponseBuilder() {
    }

    public DeleteResponseBuilder results(List<QueryResult> results) {
      this.results = results;
      return this;
    }

    public DeleteResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public DeleteResponse build() {
      DeleteResponse deleteResponse = new DeleteResponse();
      deleteResponse.setResults(results);
      deleteResponse.setRequestUUID(requestUUID);
      return deleteResponse;
    }
  }
}
