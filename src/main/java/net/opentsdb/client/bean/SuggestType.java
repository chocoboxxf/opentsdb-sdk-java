package net.opentsdb.client.bean;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * The type of data to auto complete on
 */
public enum SuggestType {
  METRICS("metrics"),
  TAGK("tagk"),
  TAGV("tagv"),
  ;

  private String name;

  SuggestType(String name) {
    this.name = name;
  }

  @JsonValue
  public String getName() {
    return name;
  }

  @Override
  public String toString() {
    return name;
  }
}
