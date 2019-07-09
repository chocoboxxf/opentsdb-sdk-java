package net.opentsdb.client.api;

import net.opentsdb.client.exception.ErrorException;

/**
 * Interface of response handlers for async API
 * @param <T> response class extends {@link net.opentsdb.client.api.BaseResponse}
 */
public interface ApiCallback<T> {

  /**
   * Handle successful response
   * @param response Class extends BaseResponse
   */
  void response(T response);

  /**
   * Handle error response (status code >= 400)
   * @param ee ErrorException 
   */
  void responseError(ErrorException ee);

  /**
   * Handle failed request
   * @param e Exception
   */
  void failed(Exception e);

}
