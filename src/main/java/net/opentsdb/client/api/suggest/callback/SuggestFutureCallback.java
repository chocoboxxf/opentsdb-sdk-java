package net.opentsdb.client.api.suggest.callback;

import java.io.IOException;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import net.opentsdb.client.api.suggest.request.SuggestRequest;
import net.opentsdb.client.api.suggest.response.SuggestResponse;
import net.opentsdb.client.exception.ErrorException;
import net.opentsdb.client.util.HttpUtil;

public class SuggestFutureCallback implements FutureCallback<HttpResponse> {
  /**
   * Request Object
   */
  private SuggestRequest request;

  /**
   * Callback Object
   */
  private SuggestCallback callback;

  public SuggestFutureCallback(SuggestRequest request,
      SuggestCallback callback) {
    this.request = request;
    this.callback = callback;
  }

  @Override
  public void completed(HttpResponse response) {
    try {
      List<String> results = HttpUtil.getResponse(response, List.class, String.class);
      SuggestResponse suggestResponse = SuggestResponse.builder()
          .requestUUID(request.getRequestUUID())
          .results(results)
          .build();
      callback.response(suggestResponse);
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
