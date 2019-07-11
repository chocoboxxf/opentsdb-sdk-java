package net.opentsdb.client.api.query.callback;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.query.request.QueryRequest;
import net.opentsdb.client.api.query.response.QueryResponse;
import net.opentsdb.client.bean.QueryResult;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;

/**
 * FutureCallback for Query API
 */
public class QueryFutureCallback implements FutureCallback<HttpResponse> {

  /**
   * Request Object
   */
  private QueryRequest request;

  /**
   * Callback Object
   */
  private QueryCallback callback;

  public QueryFutureCallback(QueryRequest request,
      QueryCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      List<QueryResult> results = HttpUtil.getResponse(response, List.class, QueryResult.class);
      QueryResponse queryResponse = QueryResponse.builder()
          .requestUUID(request.getRequestUUID())
          .results(results)
          .build();
      callback.response(queryResponse);
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
