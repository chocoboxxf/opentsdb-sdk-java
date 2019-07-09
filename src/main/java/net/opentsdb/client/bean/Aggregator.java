package net.opentsdb.client.bean;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Query aggregators 
 * @see <a href="http://opentsdb.net/docs/build/html/user_guide/query/aggregators.html#available-aggregators">Available Aggregators</>
 */
public enum Aggregator {
  AVG("avg"),
  COUNT("count"),
  DEV("dev"),
  EP50R3("ep50r3"),
  EP50R7("ep50r7"),
  EP75R3("ep75r3"),
  EP75R7("ep75r7"),
  EP90R3("ep90r3"),
  EP90R7("ep90r7"),
  EP95R3("ep95r3"),
  EP95R7("ep95r7"),
  EP99R3("ep99r3"),
  EP99R7("ep99r7"),
  EP999R3("ep999r3"),
  EP999R7("ep999r7"),
  FIRST("first"),
  LAST("last"),
  MAX("max"),
  MIMMIN("mimmin"),
  MIMMAX("mimmax"),
  MIN("min"),
  MULT("mult"),
  NONE("none"),
  P50("p50"),
  P75("p75"),
  P90("p90"),
  P95("p95"),
  P99("p99"),
  P999("p999"),
  SUM("sum"),
  ZIMSUM("zimsum"),
  ;

  private String name;

  Aggregator(String name) {
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
