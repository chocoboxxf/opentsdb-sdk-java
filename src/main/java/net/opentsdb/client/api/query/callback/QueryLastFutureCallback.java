package net.opentsdb.client.api.query.callback;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.query.request.QueryLastRequest;
import net.opentsdb.client.api.query.response.QueryLastResponse;
import net.opentsdb.client.bean.LastDataPoint;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;

/**
 * FutureCallback for Query latest data API
 */
public class QueryLastFutureCallback implements FutureCallback<HttpResponse> {

  /**
   * Request Object
   */
  private QueryLastRequest request;

  /**
   * Callback Object
   */
  private QueryLastCallback callback;

  public QueryLastFutureCallback(QueryLastRequest request,
      QueryLastCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      List<LastDataPoint> results = HttpUtil.getResponse(response, List.class, LastDataPoint.class);
      QueryLastResponse queryLastResponse = QueryLastResponse.builder()
          .requestUUID(request.getRequestUUID())
          .results(results)
          .build();
      callback.response(queryLastResponse);
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
