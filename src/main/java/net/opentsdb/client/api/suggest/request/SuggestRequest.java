package net.opentsdb.client.api.suggest.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentsdb.client.api.BaseRequest;
import net.opentsdb.client.bean.SuggestType;

/**
 * Suggest Request Object
 */
public class SuggestRequest extends BaseRequest {

  /**
   * The type of data to auto complete on. Must be one of the following: metrics, tagk or tagv
   * <br/>Example: metrics
   */
  @JsonProperty(value = "type", required = true)
  private SuggestType type;

  /**
   * A string to match on for the given type
   * <br/>Example: web
   */
  @JsonProperty("q")
  private String q;

  /**
   * The maximum number of suggested results to return. Must be greater than 0
   * <br/>Default: 25
   * <br/>Example: 10
   */
  @JsonProperty("max")
  private Integer max;

  public SuggestRequest() {
  }

  public SuggestType getType() {
    return type;
  }

  public void setType(SuggestType type) {
    this.type = type;
  }

  public String getQ() {
    return q;
  }

  public void setQ(String q) {
    this.q = q;
  }

  public Integer getMax() {
    return max;
  }

  public void setMax(Integer max) {
    this.max = max;
  }

  public static SuggestRequestBuilder builder() {
    return new SuggestRequestBuilder();
  }

  public static final class SuggestRequestBuilder {

    private SuggestType type;
    private String q;
    private String requestUUID;
    private Integer max;

    private SuggestRequestBuilder() {
    }

    public SuggestRequestBuilder type(SuggestType type) {
      this.type = type;
      return this;
    }

    public SuggestRequestBuilder q(String q) {
      this.q = q;
      return this;
    }

    public SuggestRequestBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public SuggestRequestBuilder max(Integer max) {
      this.max = max;
      return this;
    }

    public SuggestRequest build() {
      SuggestRequest suggestRequest = new SuggestRequest();
      if (requestUUID != null) {
        suggestRequest.setRequestUUID(requestUUID);
      }
      suggestRequest.setType(type);
      suggestRequest.setQ(q);
      suggestRequest.setMax(max);
      return suggestRequest;
    }
  }
}
