package net.opentsdb.client.bean;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Result for sub query
 */
public class QueryResult extends BaseBean {

  /**
   * Name of the metric retrieved for the time series
   */
  @JsonProperty("metric")
  private String metric;

  /**
   * A list of tags only returned when the results are for a single time series.
   * If results are aggregated, this value may be null or an empty map
   */
  @JsonProperty("tags")
  private Map<String, String> tags;

  /**
   * If more than one timeseries were included in the result set, i.e. they were aggregated,
   * this will display a list of tag names that were found in common across all time series.
   */
  @JsonProperty("aggregateTags")
  private List<String> aggregateTags;

  /**
   * Retrieved data points after being processed by the aggregators.
   * Each data point consists of a timestamp and a value, the format determined by the serializer.
   */
  @JsonProperty("dps")
  private LinkedHashMap<Long, Number> dps;

  /**
   * If the query retrieved annotations for timeseries over the requested timespan,
   * they will be returned in this group.
   * Annotations for every timeseries will be merged into one set and sorted by {@code start_time}.
   * Aggregator functions do not affect annotations, all annotations will be returned for the span.
   */
  @JsonProperty("annotations")
  private List<Annotation> annotations;

  /**
   * If requested by the user, the query will scan for global annotations during the timespan
   * and the results returned in this group
   */
  @JsonProperty("globalAnnotations")
  private List<Annotation> globalAnnotations;

  /**
   * TSUIDs associated with timeseries in the results
   */
  @JsonProperty("tsuids")
  private List<String> tsuids;

  /**
   * Detailed timings surrounding the query 
   */
  @JsonProperty("stats")
  private Map<String, Number> stats;

  /**
   * Original query
   */
  @JsonProperty("query")
  private Query query;

  public QueryResult() {
  }

  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  public List<String> getAggregateTags() {
    return aggregateTags;
  }

  public void setAggregateTags(List<String> aggregateTags) {
    this.aggregateTags = aggregateTags;
  }

  public LinkedHashMap<Long, Number> getDps() {
    return dps;
  }

  public void setDps(LinkedHashMap<Long, Number> dps) {
    this.dps = dps;
  }

  public List<Annotation> getAnnotations() {
    return annotations;
  }

  public void setAnnotations(List<Annotation> annotations) {
    this.annotations = annotations;
  }

  public List<Annotation> getGlobalAnnotations() {
    return globalAnnotations;
  }

  public void setGlobalAnnotations(
      List<Annotation> globalAnnotations) {
    this.globalAnnotations = globalAnnotations;
  }

  public List<String> getTsuids() {
    return tsuids;
  }

  public void setTsuids(List<String> tsuids) {
    this.tsuids = tsuids;
  }

  public Map<String, Number> getStats() {
    return stats;
  }

  public void setStats(Map<String, Number> stats) {
    this.stats = stats;
  }

  public Query getQuery() {
    return query;
  }

  public void setQuery(Query query) {
    this.query = query;
  }

  public static QueryResultBuilder builder() {
    return new QueryResultBuilder();
  }
  
  public static final class QueryResultBuilder {

    private String metric;
    private Map<String, String> tags;
    private List<String> aggregateTags;
    private LinkedHashMap<Long, Number> dps;
    private List<Annotation> annotations;
    private List<Annotation> globalAnnotations;
    private List<String> tsuids;
    private Map<String, Number> stats;
    private Query query;

    private QueryResultBuilder() {
    }

    public QueryResultBuilder metric(String metric) {
      this.metric = metric;
      return this;
    }

    public QueryResultBuilder tags(Map<String, String> tags) {
      this.tags = tags;
      return this;
    }

    public QueryResultBuilder aggregateTags(List<String> aggregateTags) {
      this.aggregateTags = aggregateTags;
      return this;
    }

    public QueryResultBuilder dps(LinkedHashMap<Long, Number> dps) {
      this.dps = dps;
      return this;
    }

    public QueryResultBuilder annotations(List<Annotation> annotations) {
      this.annotations = annotations;
      return this;
    }

    public QueryResultBuilder globalAnnotations(List<Annotation> globalAnnotations) {
      this.globalAnnotations = globalAnnotations;
      return this;
    }

    public QueryResultBuilder tsuids(List<String> tsuids) {
      this.tsuids = tsuids;
      return this;
    }

    public QueryResultBuilder stats(Map<String, Number> stats) {
      this.stats = stats;
      return this;
    }

    public QueryResultBuilder query(Query query) {
      this.query = query;
      return this;
    }

    public QueryResult build() {
      QueryResult queryResult = new QueryResult();
      queryResult.setMetric(metric);
      queryResult.setTags(tags);
      queryResult.setAggregateTags(aggregateTags);
      queryResult.setDps(dps);
      queryResult.setAnnotations(annotations);
      queryResult.setGlobalAnnotations(globalAnnotations);
      queryResult.setTsuids(tsuids);
      queryResult.setStats(stats);
      queryResult.setQuery(query);
      return queryResult;
    }
  }
}
