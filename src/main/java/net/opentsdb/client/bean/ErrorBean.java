package net.opentsdb.client.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Error response content
 */
public class ErrorBean extends BaseBean {

  /**
   * The HTTP status code
   * <br/>Example: 400
   */
  @JsonProperty("code")
  private Integer code;

  /**
   * A descriptive error message about what went wrong
   * <br/>Example: Missing required parameter
   */
  @JsonProperty("message")
  private String message;

  /**
   * Details about the error, often a stack trace
   * <br/>Example: Missing value: type
   */
  @JsonProperty("details")
  private String details;

  /**
   * A JAVA stack trace describing the location where the error was generated.
   * This can be disabled via the {@code tsd.http.show_stack_trace} configuration option.
   * The default for TSD is to show the stack trace.
   */
  @JsonProperty("trace")
  private String trace;

  public ErrorBean() {
  }

  public Integer getCode() {
    return code;
  }

  public void setCode(Integer code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getDetails() {
    return details;
  }

  public void setDetails(String details) {
    this.details = details;
  }

  public String getTrace() {
    return trace;
  }

  public void setTrace(String trace) {
    this.trace = trace;
  }
  
  public static ErrorBuilder builder() {
    return new ErrorBuilder();
  }

  public static final class ErrorBuilder {

    private Integer code;
    private String message;
    private String details;
    private String trace;

    private ErrorBuilder() {
    }
    
    public ErrorBuilder code(Integer code) {
      this.code = code;
      return this;
    }

    public ErrorBuilder message(String message) {
      this.message = message;
      return this;
    }

    public ErrorBuilder details(String details) {
      this.details = details;
      return this;
    }

    public ErrorBuilder trace(String trace) {
      this.trace = trace;
      return this;
    }

    public ErrorBean build() {
      ErrorBean error = new ErrorBean();
      error.setCode(code);
      error.setMessage(message);
      error.setDetails(details);
      error.setTrace(trace);
      return error;
    }
  }
}
