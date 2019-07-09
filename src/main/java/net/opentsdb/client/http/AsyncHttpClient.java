package net.opentsdb.client.http;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.nio.reactor.IOReactorException;
import org.slf4j.Logger;

import net.opentsdb.client.OpenTSDBConfig;
import net.opentsdb.client.http.callback.GracefulCloseFutureCallback;
import net.opentsdb.client.http.strategy.DefaultKeepAliveStrategy;

public class AsyncHttpClient extends BaseHttpClient {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(AsyncHttpClient.class);

  private static final AtomicInteger CLEANER_COUNT = new AtomicInteger();

  private final OpenTSDBConfig config;

  private final AtomicInteger taskNumber;

  private CloseableHttpAsyncClient client;

  private ConnectionCleaner connectionCleaner;

  AsyncHttpClient(OpenTSDBConfig config) throws IOReactorException {
    this.config = config;
    this.taskNumber = new AtomicInteger(0);
    
    IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
        .setIoThreadCount(Runtime.getRuntime()
            .availableProcessors())
        .build();
    ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);
    
    PoolingNHttpClientConnectionManager cm = new PoolingNHttpClientConnectionManager(ioReactor);
    cm.setMaxTotal(config.getMaxConnections());
    cm.setDefaultMaxPerRoute(config.getMaxPerRoute());

    RequestConfig requestConfig = RequestConfig.custom()
        .setConnectTimeout(config.getConnectTimeout() * 1000)
        .setSocketTimeout(config.getSocketTimeout() * 1000)
        .setConnectionRequestTimeout(config.getConnectionRequestTimeout() * 1000)
        .build();
    
    HttpAsyncClientBuilder clientBuilder = HttpAsyncClientBuilder.create()
        .setConnectionManager(cm)
        .setDefaultRequestConfig(requestConfig);

    if (!config.isReadOnly()) {
      clientBuilder.setKeepAliveStrategy(new DefaultKeepAliveStrategy());
    }
    
    client = clientBuilder.build();
    client.start();
    
    connectionCleaner = new ConnectionCleaner(cm);
    connectionCleaner.start();
  }

  public Future<HttpResponse> get(String path, Map<String, String> params,
      FutureCallback<HttpResponse> httpCallback)
      throws URISyntaxException {
    log.debug("[HTTP GET] PATH: {}，PARAMS: {}", path, params);
    URI uri = getUri(config.getHost(), config.getPort(), path, params);
    HttpGet httpGet = new HttpGet(uri);
    return client.execute(httpGet, httpCallback);
  }

  public Future<HttpResponse> post(String path, String jsonBody,
      FutureCallback<HttpResponse> httpCallback) throws URISyntaxException {
    return post(path, jsonBody, null, httpCallback);
  }

  public Future<HttpResponse> post(String path, String jsonBody, Map<String, String> params,
      FutureCallback<HttpResponse> httpCallback)
      throws URISyntaxException {
    log.debug("[HTTP POST] PATH: {}，BODY: {}, PARAMS: {}", path, jsonBody, params);
    URI uri = getUri(config.getHost(), config.getPort(), path, params);
    HttpPost httpPost = getPostRequest(uri, jsonBody);

    FutureCallback<HttpResponse> responseCallback = null;
    if (httpCallback != null) {
      log.debug("Task Number: {}", taskNumber.incrementAndGet());
      responseCallback = new GracefulCloseFutureCallback(taskNumber, httpCallback);
    }

    return client.execute(httpPost, responseCallback);
  }
  
  public void close(boolean force) throws IOException {
    if (!force) {
      while (client.isRunning()) {
        int i = this.taskNumber.get();
        if (i == 0) {
          break;
        } else {
          try {
            Thread.sleep(50);
          } catch (InterruptedException e) {
            log.warn("The thread {} is Interrupted", Thread.currentThread()
                .getName());
          }
        }
      }
    }
    connectionCleaner.stop();
    client.close();
  }

  private static class ConnectionCleaner implements Runnable {

    private static final int DEFAULT_MAX_IDLE = 30;
    private static final int MIN_PERIOD = 30;

    private int maxIdle;

    private ScheduledExecutorService executorService;

    private PoolingNHttpClientConnectionManager cm;

    public ConnectionCleaner(PoolingNHttpClientConnectionManager cm, int maxIdle) {
      this.maxIdle = maxIdle;
      this.cm = cm;
      executorService = Executors.newSingleThreadScheduledExecutor(
          (r) -> {
            Thread t = new Thread(r, "AsyncHttpClientCleaner-" + CLEANER_COUNT.incrementAndGet());
            t.setDaemon(true);
            return t;
          }
      );
    }

    public ConnectionCleaner(PoolingNHttpClientConnectionManager cm) {
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
