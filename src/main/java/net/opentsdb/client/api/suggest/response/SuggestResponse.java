package net.opentsdb.client.api.suggest.response;

import java.util.List;

import net.opentsdb.client.api.BaseResponse;

/**
 * Suggest Response Object
 */
public class SuggestResponse extends BaseResponse {

  /**
   * suggest result list
   */
  private List<String> results;

  public SuggestResponse() {
  }

  public List<String> getResults() {
    return results;
  }

  public void setResults(List<String> results) {
    this.results = results;
  }

  public static SuggestResponseBuilder builder() {
    return new SuggestResponseBuilder();
  }
  
  public static final class SuggestResponseBuilder {

    private List<String> results;
    private String requestUUID;

    private SuggestResponseBuilder() {
    }

    public SuggestResponseBuilder results(List<String> results) {
      this.results = results;
      return this;
    }

    public SuggestResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public SuggestResponse build() {
      SuggestResponse suggestResponse = new SuggestResponse();
      suggestResponse.setResults(results);
      suggestResponse.setRequestUUID(requestUUID);
      return suggestResponse;
    }
  }
}
