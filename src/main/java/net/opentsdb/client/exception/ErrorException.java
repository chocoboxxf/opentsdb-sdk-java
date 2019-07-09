package net.opentsdb.client.exception;

import net.opentsdb.client.bean.ErrorBean;

/**
 * Exception thrown by error response
 */
public class ErrorException extends RuntimeException {

  private int code;

  private String message;

  private String details;

  private String trace;

  public ErrorException(int code, String message, String details, String trace) {
    super(message);
    this.code = code;
    this.message = message;
    this.details = details;
    this.trace = trace;
  }

  public int getCode() {
    return code;
  }

  @Override
  public String getMessage() {
    return message;
  }

  public String getDetails() {
    return details;
  }

  public String getTrace() {
    return trace;
  }

  public static ErrorException build(ErrorBean error) {
    return new ErrorException(
        error.getCode(),
        error.getMessage(),
        error.getDetails(),
        error.getTrace()
    );
  }

}
