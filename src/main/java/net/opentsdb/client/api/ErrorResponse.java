package net.opentsdb.client.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentsdb.client.bean.ErrorBean;

/**
 * Base class for error response
 */
public class ErrorResponse extends BaseResponse {

  /**
   * Error content
   */
  @JsonProperty("error")
  private ErrorBean error;

  public ErrorResponse() {
  }

  public ErrorBean getError() {
    return error;
  }

  public void setError(ErrorBean error) {
    this.error = error;
  }

  public static ErrorResponseBuilder builder() {
    return new ErrorResponseBuilder();
  }

  public static final class ErrorResponseBuilder {

    private String requestUUID;
    private ErrorBean error;

    private ErrorResponseBuilder() {
    }
    
    public ErrorResponseBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public ErrorResponseBuilder error(ErrorBean error) {
      this.error = error;
      return this;
    }

    public ErrorResponse build() {
      ErrorResponse errorResponse = new ErrorResponse();
      errorResponse.setRequestUUID(requestUUID);
      errorResponse.setError(error);
      return errorResponse;
    }
  }
}
