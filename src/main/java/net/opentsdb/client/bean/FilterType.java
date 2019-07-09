package net.opentsdb.client.bean;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Built-in query filters
 * @see <a href="http://opentsdb.net/docs/build/html/user_guide/query/filters.html#built-in-2-x-filters">Built-in 2.x Filters</a>
 */
public enum FilterType {
  ILITERAL_OR("iliteral_or"),
  IWILDCARD("iwildcard"),
  LITERAL_OR("literal_or"),
  NOT_ILITERAL_OR("not_iliteral_or"),
  NOT_LITERAL_OR("not_literal_or"),
  REGEXP("regexp"),
  WILDCARD("wildcard"),
  ;

  private String name;

  FilterType(String name) {
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
