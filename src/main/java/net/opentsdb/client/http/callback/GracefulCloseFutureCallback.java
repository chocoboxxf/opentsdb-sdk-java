package net.opentsdb.client.http.callback;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.slf4j.Logger;

public class GracefulCloseFutureCallback implements FutureCallback<HttpResponse> {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(GracefulCloseFutureCallback.class);

  private final AtomicInteger taskNum;
  
  private final FutureCallback<HttpResponse> futureCallback;

  public GracefulCloseFutureCallback(AtomicInteger taskNum, FutureCallback<HttpResponse> futureCallback) {
    super();
    this.taskNum = taskNum;
    this.futureCallback = futureCallback;
  }

  @Override
  public void completed(HttpResponse result) {
    futureCallback.completed(result);
    log.debug("Task Number: {}", taskNum.decrementAndGet());
  }

  @Override
  public void failed(Exception ex) {
    futureCallback.failed(ex);
    log.debug("Task Number: {}", taskNum.decrementAndGet());
  }

  @Override
  public void cancelled() {
    futureCallback.cancelled();
    log.debug("Task Number: {}", taskNum.decrementAndGet());
  }

}