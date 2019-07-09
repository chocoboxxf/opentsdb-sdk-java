package net.opentsdb.client.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

public class JsonUtil {
  private static ObjectMapper instance;

  static {
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    instance = new ObjectMapper();
    instance.setDateFormat(dateFormat);
    instance.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    instance.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
    instance.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    instance.setSerializationInclusion(JsonInclude.Include.NON_NULL);
  }

  public static String writeValueAsString(Object value) throws JsonProcessingException {
    if (value == null) {
      return "";
    }
    return instance.writeValueAsString(value);
  }

  public static <T> T readValue(String content, Class<T> valueType) throws IOException {
    if (content == null) {
      return null;
    }
    return instance.readValue(content, valueType);
  }

  public static <T> T readValue(String content, Class<? extends Collection> collectionClass,
      Class<?> elementClass) throws IOException {
    if (content == null) {
      return null;
    }
    CollectionType collectionType = instance.getTypeFactory()
        .constructCollectionType(collectionClass, elementClass);
    return instance.readValue(content, collectionType);
  }
}
