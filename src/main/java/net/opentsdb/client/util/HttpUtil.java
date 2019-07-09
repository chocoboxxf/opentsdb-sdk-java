package net.opentsdb.client.util;

import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import net.opentsdb.client.api.ErrorResponse;
import net.opentsdb.client.exception.ErrorException;

public class HttpUtil {
  
  public static String getContent(HttpResponse response) throws IOException, ErrorException {
    return getContent(response, false);
  }

  public static String getContent(HttpResponse response, boolean ignoreStatus) throws IOException, ErrorException {
    String content = HttpUtil.getContentString(response);
    if (!ignoreStatus && response.getStatusLine().getStatusCode() >= 400) {
      ErrorResponse error = JsonUtil.readValue(content, ErrorResponse.class);
      throw ErrorException.build(error.getError());
    }
    return content;
  }

  private static String getContentString(HttpResponse response) throws IOException {
    HttpEntity entity = response.getEntity();
    if (entity != null) {
      return EntityUtils.toString(entity, Charset.defaultCharset());
    }
    return null;
  }


}
