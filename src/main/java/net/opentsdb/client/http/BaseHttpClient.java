package net.opentsdb.client.http;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;

abstract class BaseHttpClient {

  URI getUri(String host, int port, String path, Map<String, String> params)
      throws URISyntaxException {
    String url = "http://" + host + ":" + port + path;
    URIBuilder uriBuilder = new URIBuilder(url);
    if (params != null && params.size() > 0) {
      for (String paramKey : params.keySet()) {
        uriBuilder.addParameter(paramKey, params.get(paramKey));
      }
    }
    return uriBuilder.build();
  }

  HttpPost getPostRequest(URI uri, String jsonBody) {
    HttpPost httpPost = new HttpPost(uri);

    if (StringUtils.isNoneBlank(jsonBody)) {
      httpPost.addHeader("Content-Type", "application/json");
      StringEntity stringEntity = new StringEntity(jsonBody, Charset.forName("UTF-8"));
      httpPost.setEntity(stringEntity);
    }

    return httpPost;
  }

  HttpPut getPutRequest(URI uri, String jsonBody) {
    HttpPut httpPut  = new HttpPut(uri);

    if (StringUtils.isNoneBlank(jsonBody)) {
      httpPut.addHeader("Content-Type", "application/json");
      StringEntity stringEntity = new StringEntity(jsonBody, Charset.forName("UTF-8"));
      httpPut.setEntity(stringEntity);
    }

    return httpPut;
  }

}
