package net.opentsdb.client.bean;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Query objects for querying or deleting request
 */
public class Query extends BaseBean {

  /**
   * The name of an aggregation function to use.
   * <br/>Example: sum
   * @see <a href="http://opentsdb.net/docs/build/html/user_guide/query/aggregators.html#available-aggregators">Available Aggregators</>
   */
  @JsonProperty(value = "aggregator", required = true)
  private Aggregator aggregator;

  /**
   * The name of a metric stored in the system
   * <br/>Example: sys.cpu.0
   */
  @JsonProperty(value = "metric", required = true)
  private String metric;

  /**
   * Whether or not the data should be converted into deltas before returning.
   * This is useful if the metric is a continuously incrementing counter
   * and you want to view the rate of change between data points.
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("rate")
  private Boolean rate;

  /**
   * Monotonically increasing counter handling options
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/query/index.html#rate-options">Rate Options</a>
   */
  @JsonProperty("rateOptions")
  private RateOptions rateOptions;

  /**
   * An optional downsampling function to reduce the amount of data returned.
   * <br/>Example: 5m-avg
   */
  @JsonProperty("downsample")
  private String downsample;

  /**
   * To drill down to specific timeseries or group results by tag,
   * supply one or more map values in the same format as the query string.
   * Tags are converted to filters in 2.2. See the notes below about conversions.
   * Note that if no tags are specified,
   * all metrics in the system will be aggregated into the results.
   * <br/>Deprecated in 2.2
   */
  @Deprecated
  @JsonProperty("tags")
  private Map<String, String> tags;

  /**
   * Filters the time series emitted in the results. Note that if no filters are specified,
   * all time series for the given metric will be aggregated into the results.
   */
  @JsonProperty("filters")
  private List<Filter> filters;

  /**
   * Returns the series that include only the tag keys provided in the filters.
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("explicitTags")
  private Boolean explicitTags;

  /**
   * Fetches histogram data for the metric and computes the given list of percentiles on the data.
   * Percentiles are floating point values from 0 to 100. More details below.
   * <br/>Example: [99.9, 95.0, 75.0]
   */
  @JsonProperty("percentiles")
  private List<Float> percentiles;

  /**
   * An optional fallback mode when fetching rollup data. Can either be 
   * {@code ROLLUP_RAW} to skip rollups,
   * {@code ROLLUP_NOFALLBACK} to only query the auto-detected rollup table,
   * {@code ROLLUP_FALLBACK} to fallback to matching rollup tables in sequence or
   * {@code ROLLUP_FALLBACK_RAW} to fall back to the raw table if nothing was found in the first auto table.
   * <br/>Default: ROLLUP_NOFALLBACK
   * <br/>Example: ROLLUP_RAW
   */
  @JsonProperty("rollupUsage")
  private String rollupUsage;

  public Query() {
  }

  public Aggregator getAggregator() {
    return aggregator;
  }

  public void setAggregator(Aggregator aggregator) {
    this.aggregator = aggregator;
  }

  public String getMetric() {
    return metric;
  }

  public void setMetric(String metric) {
    this.metric = metric;
  }

  public Boolean getRate() {
    return rate;
  }

  public void setRate(Boolean rate) {
    this.rate = rate;
  }

  public RateOptions getRateOptions() {
    return rateOptions;
  }

  public void setRateOptions(RateOptions rateOptions) {
    this.rateOptions = rateOptions;
  }

  public String getDownsample() {
    return downsample;
  }

  public void setDownsample(String downsample) {
    this.downsample = downsample;
  }

  public Map<String, String> getTags() {
    return tags;
  }

  public void setTags(Map<String, String> tags) {
    this.tags = tags;
  }

  public List<Filter> getFilters() {
    return filters;
  }

  public void setFilters(List<Filter> filters) {
    this.filters = filters;
  }

  public Boolean getExplicitTags() {
    return explicitTags;
  }

  public void setExplicitTags(Boolean explicitTags) {
    this.explicitTags = explicitTags;
  }

  public List<Float> getPercentiles() {
    return percentiles;
  }

  public void setPercentiles(List<Float> percentiles) {
    this.percentiles = percentiles;
  }

  public String getRollupUsage() {
    return rollupUsage;
  }

  public void setRollupUsage(String rollupUsage) {
    this.rollupUsage = rollupUsage;
  }

  public static QueryBuilder builder() {
    return new QueryBuilder();
  }

  public static final class QueryBuilder {

    private Aggregator aggregator;
    private String metric;
    private Boolean rate;
    private RateOptions rateOptions;
    private String downsample;
    private Map<String, String> tags;
    private List<Filter> filters;
    private Boolean explicitTags;
    private List<Float> percentiles;
    private String rollupUsage;

    private QueryBuilder() {
    }

    public QueryBuilder aggregator(Aggregator aggregator) {
      this.aggregator = aggregator;
      return this;
    }

    public QueryBuilder metric(String metric) {
      this.metric = metric;
      return this;
    }

    public QueryBuilder rate(Boolean rate) {
      this.rate = rate;
      return this;
    }

    public QueryBuilder rateOptions(RateOptions rateOptions) {
      this.rateOptions = rateOptions;
      return this;
    }

    public QueryBuilder downsample(String downsample) {
      this.downsample = downsample;
      return this;
    }

    public QueryBuilder tags(Map<String, String> tags) {
      this.tags = tags;
      return this;
    }

    public QueryBuilder filters(List<Filter> filters) {
      this.filters = filters;
      return this;
    }

    public QueryBuilder explicitTags(Boolean explicitTags) {
      this.explicitTags = explicitTags;
      return this;
    }

    public QueryBuilder percentiles(List<Float> percentiles) {
      this.percentiles = percentiles;
      return this;
    }

    public QueryBuilder rollupUsage(String rollupUsage) {
      this.rollupUsage = rollupUsage;
      return this;
    }

    public Query build() {
      Query query = new Query();
      query.setAggregator(aggregator);
      query.setMetric(metric);
      query.setRate(rate);
      query.setRateOptions(rateOptions);
      query.setDownsample(downsample);
      query.setTags(tags);
      query.setFilters(filters);
      query.setExplicitTags(explicitTags);
      query.setPercentiles(percentiles);
      query.setRollupUsage(rollupUsage);
      return query;
    }
  }
}
