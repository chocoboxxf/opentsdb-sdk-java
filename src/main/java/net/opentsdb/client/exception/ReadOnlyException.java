package net.opentsdb.client.exception;

/**
 * Exception thrown by manipulating data in read-only-mode client
 */
public class ReadOnlyException extends RuntimeException {

  public ReadOnlyException() {
    super("Current client is in read only mode");
  }

}
