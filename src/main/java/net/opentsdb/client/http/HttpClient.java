package net.opentsdb.client.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;

import net.opentsdb.client.OpenTSDBConfig;
import net.opentsdb.client.http.strategy.DefaultKeepAliveStrategy;

public class HttpClient extends BaseHttpClient {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(HttpClient.class);

  private static final AtomicInteger CLEANER_COUNT = new AtomicInteger();

  private final OpenTSDBConfig config;

  private CloseableHttpClient client;

  private ConnectionCleaner connectionCleaner;

  HttpClient(OpenTSDBConfig config) {
    this.config = config;
    
    PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    cm.setMaxTotal(config.getMaxConnections());
    cm.setDefaultMaxPerRoute(config.getMaxPerRoute());

    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(config.getConnectTimeout() * 1000)
        .setSocketTimeout(config.getSocketTimeout() * 1000)
        .setConnectionRequestTimeout(config.getConnectionRequestTimeout() * 1000)
        .build();

    HttpClientBuilder clientBuilder = HttpClientBuilder.create()
        .setConnectionManager(cm)
        .setDefaultRequestConfig(requestConfig);
        
    if (!config.isReadOnly()) {
      clientBuilder.setKeepAliveStrategy(new DefaultKeepAliveStrategy());
    }

    client = clientBuilder.build();
    
    connectionCleaner = new ConnectionCleaner(cm);
    connectionCleaner.start();
  }

  public void close() throws IOException {
    connectionCleaner.stop();
    client.close();
  }

  public HttpResponse get(String path, Map<String, String> params)
      throws IOException, URISyntaxException {
    log.debug("[HTTP GET] PATH: {}，PARAMS: {}", path, params);
    URI uri = getUri(config.getHost(), config.getPort(), path, params);
    HttpGet httpGet = new HttpGet(uri);
    return client.execute(httpGet);
  }

  public HttpResponse post(String path, String jsonBody) throws IOException, URISyntaxException {
    return post(path, jsonBody, null);
  }

  public HttpResponse post(String path, String jsonBody, Map<String, String> params)
      throws IOException, URISyntaxException {
    log.debug("[HTTP POST] PATH: {}，BODY: {}, PARAMS: {}", path, jsonBody, params);
    URI uri = getUri(config.getHost(), config.getPort(), path, params);
    HttpPost httpPost = getPostRequest(uri, jsonBody);
    return client.execute(httpPost);
  }
  
  public HttpResponse delete(String path, Map<String, String> params)
      throws IOException, URISyntaxException {
    log.debug("[HTTP DELETE] PATH: {}，PARAMS: {}", path, params);
    URI uri = getUri(config.getHost(), config.getPort(), path, params);
    HttpDelete httpDelete = new HttpDelete(uri);
    return client.execute(httpDelete);
  }

  public HttpResponse put(String path, String jsonBody) throws IOException, URISyntaxException {
    return put(path, jsonBody, null);
  }
  
  public HttpResponse put(String path, String jsonBody, Map<String, String> params)
      throws IOException, URISyntaxException {
    log.debug("[HTTP PUT] PATH: {}，BODY: {}, PARAMS: {}", path, jsonBody, params);
    URI uri = getUri(config.getHost(), config.getPort(), path, params);
    HttpPut httpPut = getPutRequest(uri, jsonBody);
    return client.execute(httpPut);
  }

  private static class ConnectionCleaner implements Runnable {
    
    private static final int DEFAULT_MAX_IDLE = 30;
    private static final int MIN_PERIOD = 30;

    private int maxIdle;
    
    private ScheduledExecutorService executorService;
    
    private PoolingHttpClientConnectionManager cm;

    public ConnectionCleaner(PoolingHttpClientConnectionManager cm, int maxIdle) {
      this.maxIdle = maxIdle;
      this.cm = cm;
      executorService = Executors.newSingleThreadScheduledExecutor(
          (r) -> {
            Thread t = new Thread(r, "HttpClientCleaner-" + CLEANER_COUNT.incrementAndGet());
            t.setDaemon(true);
            return t;
          }
      );
    }

    public ConnectionCleaner(PoolingHttpClientConnectionManager cm) {
      this(cm, DEFAULT_MAX_IDLE);
    }
    
    public void start() {
      executorService.scheduleAtFixedRate(
          this,
          maxIdle < MIN_PERIOD ? MIN_PERIOD : maxIdle,
          maxIdle < MIN_PERIOD ? MIN_PERIOD : maxIdle,
          TimeUnit.SECONDS
      );
    }
    
    public void stop() {
      executorService.shutdownNow();
    }

    @Override
    public void run() {
      try {
        log.debug("Close idle connections");
        cm.closeExpiredConnections();
        cm.closeIdleConnections(maxIdle, TimeUnit.SECONDS);
      } catch (Exception ex) {
        log.error("", ex);
      }
    }
  }

}
