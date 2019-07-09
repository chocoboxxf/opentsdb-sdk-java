package net.opentsdb.client.http.strategy;

import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;

public class DefaultKeepAliveStrategy implements ConnectionKeepAliveStrategy {
  
  private static final Long DEFAULT_TIMEOUT = 60000L;

  @Override
  public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
    HeaderElementIterator it = new BasicHeaderElementIterator
        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
    while (it.hasNext()) {
      HeaderElement he = it.nextElement();
      String param = he.getName();
      String value = he.getValue();
      if (value != null && param.equalsIgnoreCase
          ("timeout")) {
        return Long.parseLong(value) * 1000;
      }
    }
    return DEFAULT_TIMEOUT;
  }

}
