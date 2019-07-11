package net.opentsdb.client.api.delete.callback;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.delete.request.DeleteRequest;
import net.opentsdb.client.api.delete.response.DeleteResponse;
import net.opentsdb.client.bean.QueryResult;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;

/**
 * FutureCallback for Delete API
 */
public class DeleteFutureCallback implements FutureCallback<HttpResponse> {

  /**
   * Request Object
   */
  private DeleteRequest request;

  /**
   * Callback Object
   */
  private DeleteCallback callback;

  public DeleteFutureCallback(DeleteRequest request,
      DeleteCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      List<QueryResult> results = HttpUtil.getResponse(response, List.class, QueryResult.class);
      DeleteResponse deleteResponse = DeleteResponse.builder()
          .requestUUID(request.getRequestUUID())
          .results(results)
          .build();
      callback.response(deleteResponse);
    } catch (ErrorException ee) {
      this.callback.responseError(ee);
    } catch (IOException e) {
      e.printStackTrace();
      this.callback.failed(e);
    }
  }

  @Override
  public void failed(Exception e) {
    e.printStackTrace();
    this.callback.failed(e);
  }

  @Override
  public void cancelled() {
    // Not implemented
  }
  
}
