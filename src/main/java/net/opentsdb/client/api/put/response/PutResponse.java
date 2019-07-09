package net.opentsdb.client.api.put.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentsdb.client.api.BaseResponse;
import net.opentsdb.client.bean.PutError;

/**
 * Put Response Object
 */
public class PutResponse extends BaseResponse {

  /**
   * The number of data points that were queued successfully for storage
   */
  @JsonProperty("success")
  private Integer success;

  /**
   * The number of data points that could not be queued for storage
   */
  @JsonProperty("failed")
  private Integer failed;

  /**
   * A list of data points that failed be queued and why.
   * Present in the {@code details} response only.
   */
  @JsonProperty("errors")
  private List<PutError> errors;

  public PutResponse() {
  }

  public Integer getSuccess() {
    return success;
  }

  public void setSuccess(Integer success) {
    this.success = success;
  }

  public Integer getFailed() {
    return failed;
  }

  public void setFailed(Integer failed) {
    this.failed = failed;
  }

  public List<PutError> getErrors() {
    return errors;
  }

  public void setErrors(List<PutError> errors) {
    this.errors = errors;
  }

  public static PutResponseBuilder builder() {
    return new PutResponseBuilder();
  }

  public static final class PutResponseBuilder {

    private String requestUUID;
    private Integer success;
    private Integer failed;
    private List<PutError> errors;

    private PutResponseBuilder() {
    }

    public PutResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public PutResponseBuilder success(Integer success) {
      this.success = success;
      return this;
    }

    public PutResponseBuilder failed(Integer failed) {
      this.failed = failed;
      return this;
    }

    public PutResponseBuilder errors(List<PutError> errors) {
      this.errors = errors;
      return this;
    }

    public PutResponse build() {
      PutResponse putResponse = new PutResponse();
      putResponse.setRequestUUID(requestUUID);
      putResponse.setSuccess(success);
      putResponse.setFailed(failed);
      putResponse.setErrors(errors);
      return putResponse;
    }
  }
}
