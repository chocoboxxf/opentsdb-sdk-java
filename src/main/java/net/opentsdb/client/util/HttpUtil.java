package net.opentsdb.client.util;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Collection;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import net.opentsdb.client.api.ErrorResponse;
import net.opentsdb.client.exception.ErrorException;

public class HttpUtil {

  public static <T> T getResponse(HttpResponse response, Class<T> valueType) throws IOException {
    String content = HttpUtil.getContentString(response);
    int statusCode = response.getStatusLine().getStatusCode();
    String message = response.getStatusLine().getReasonPhrase();

    if (statusCode >= HttpStatus.SC_BAD_REQUEST) {
      // error response
      return getErrorResponse(statusCode, message, content, valueType);
    } else if (statusCode == HttpStatus.SC_NO_CONTENT) {
      // empty response
      return null;
    }
    // normal response
    return JsonUtil.readValue(content, valueType);
  }

  public static <T> T getResponse(HttpResponse response,
      Class<? extends Collection> collectionClass,
      Class<?> elementClass) throws IOException {
    String content = HttpUtil.getContentString(response);
    int statusCode = response.getStatusLine().getStatusCode();
    String message = response.getStatusLine().getReasonPhrase();

    if (statusCode >= HttpStatus.SC_BAD_REQUEST) {
      // error response
      return getErrorResponse(statusCode, message, content, collectionClass, elementClass);
    } else if (statusCode == HttpStatus.SC_NO_CONTENT) {
      // generate empty response
      return null;
    }
    // normal response
    return JsonUtil.readValue(content, collectionClass, elementClass);
  }

  private static <T> T getErrorResponse(int statusCode, String reasonPhrase, String content,
      Class<T> valueType) throws IOException {
    // try to generate error response
    ErrorResponse error = JsonUtil.readValue(content, ErrorResponse.class);
    if (error != null && error.getError() != null) {
      throw ErrorException.build(error.getError());
    }
    // try to generate normal response
    T result = JsonUtil.readValue(content, valueType);
    if (result != null) {
      return result;
    }
    // other error
    throw new ErrorException(
        statusCode,
        reasonPhrase,
        "",
        ""
    );
  }

  private static <T> T getErrorResponse(int statusCode, String reasonPhrase, String content,
      Class<? extends Collection> collectionClass, Class<?> elementClass) throws IOException {
    // try to generate error response
    ErrorResponse error = JsonUtil.readValue(content, ErrorResponse.class);
    if (error != null && error.getError() != null) {
      throw ErrorException.build(error.getError());
    }
    // try to generate normal response
    T result = JsonUtil.readValue(content, collectionClass, elementClass);
    if (result != null) {
      return result;
    }
    // other error
    throw new ErrorException(
        statusCode,
        reasonPhrase,
        "",
        ""
    );
  }

  private static String getContentString(HttpResponse response) throws IOException {
    HttpEntity entity = response.getEntity();
    if (entity != null) {
      return EntityUtils.toString(entity, Charset.defaultCharset());
    }
    return null;
  }

}
