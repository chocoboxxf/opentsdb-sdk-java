package net.opentsdb.client.http;

import org.apache.http.nio.reactor.IOReactorException;

import net.opentsdb.client.OpenTSDBConfig;

public class HttpClientFactory {
  
  private static HttpClientFactory httpClientFactory;
  
  static {
    httpClientFactory = new HttpClientFactory();
  }

  public static HttpClientFactory getInstance() {
    return httpClientFactory;
  }

  public HttpClient getHttpClient(OpenTSDBConfig config) {
    return new HttpClient(config);
  }

  public AsyncHttpClient getAsyncHttpClient(OpenTSDBConfig config)
      throws IOReactorException {
    return new AsyncHttpClient(config);
  }

}
