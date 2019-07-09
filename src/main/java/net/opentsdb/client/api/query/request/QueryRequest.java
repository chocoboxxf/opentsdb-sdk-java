package net.opentsdb.client.api.query.request;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import net.opentsdb.client.api.BaseRequest;
import net.opentsdb.client.bean.Query;

/**
 * Query Request Object
 */
public class QueryRequest extends BaseRequest {
  /**
   * The start time for the query. 
   * This can be a relative or absolute timestamp.
   * <br/>Example: 1h-ago
   * @see <a href="http://opentsdb.net/docs/build/html/user_guide/query/dates.html">Dates and Times</a>
   */
  @JsonProperty(value = "start", required = true)
  private String start;

  /**
   * An end time for the query. If not supplied, 
   * the TSD will assume the local system time on the server.
   * This may be a relative or absolute timestamp.
   * <br/>Default: {@code current time}
   * <br/>Example: 1s-ago
   * @see <a href="http://opentsdb.net/docs/build/html/user_guide/query/dates.html">Dates and Times</a>
   */
  @JsonProperty("end")
  private String end;

  /**
   * One or more sub queries used to select the time series to return.
   */
  @JsonProperty("queries")
  private List<Query> queries;

  /**
   * Whether or not to return annotations with a query.
   * The default is to return annotations for the requested timespan
   * but this flag can disable the return.
   * This affects both local and global notes and overrides {@code globalAnnotations}
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("noAnnotations")
  private Boolean noAnnotations;

  /**
   * Whether or not the query should retrieve global annotations for the requested timespan
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("globalAnnotations")
  private Boolean globalAnnotations;

  /**
   * Whether or not to output data point timestamps in milliseconds or seconds. 
   * The msResolution flag is recommended. 
   * If this flag is not provided and there are multiple data points within a second,
   * those data points will be down sampled using the query's aggregation function.
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("msResolution")
  private Boolean msResolution;

  /**
   * Whether or not to output the TSUIDs associated with timeseries in the results.
   * If multiple time series were aggregated into one set,
   * multiple TSUIDs will be returned in a sorted manner
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("showTSUIDs")
  private Boolean showTSUIDs;

  /**
   * stats summary not supported
   */
  // @JsonProperty("showSummary")
  // private Boolean showSummary;

  /**
   * Whether or not to show detailed timings surrounding the query in the results.
   * This creates another object in the map that is unlike the data point objects.
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("showStats")
  private Boolean showStats;

  /**
   * Whether or not to return the original sub query with the query results.
   * If the request contains many sub queries then this is a good way to determine
   * which results belong to which sub query. 
   * Note that in the case of a {@code *} or wildcard query, this can produce a lot of duplicate output.
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("showQuery")
  private Boolean showQuery;

  /**
   * removed, please use {@link net.opentsdb.client.api.delete.request.DeleteRequest} 
   */
  // @JsonProperty("delete")
  // private Boolean delete;

  /**
   * An optional timezone for calendar-based downsampling.
   * Must be a valid timezone database name supported by the JRE installed on the TSD server.
   * <br/>Default: UTC
   * <br/>Example: Asia/Kabul
   * @see <a href="https://en.wikipedia.org/wiki/List_of_tz_database_time_zones">timezone</a>
   */
  @JsonProperty("timezone")
  private String timezone;

  /**
   * Whether or not use the calendar based on the given timezone for downsampling intervals
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("useCalendar")
  private Boolean useCalendar;
  
  public QueryRequest() {
  }

  public String getStart() {
    return start;
  }

  public void setStart(String start) {
    this.start = start;
  }

  public String getEnd() {
    return end;
  }

  public void setEnd(String end) {
    this.end = end;
  }

  public List<Query> getQueries() {
    return queries;
  }

  public void setQueries(List<Query> queries) {
    this.queries = queries;
  }

  public Boolean getNoAnnotations() {
    return noAnnotations;
  }

  public void setNoAnnotations(Boolean noAnnotations) {
    this.noAnnotations = noAnnotations;
  }

  public Boolean getGlobalAnnotations() {
    return globalAnnotations;
  }

  public void setGlobalAnnotations(Boolean globalAnnotations) {
    this.globalAnnotations = globalAnnotations;
  }

  public Boolean getMsResolution() {
    return msResolution;
  }

  public void setMsResolution(Boolean msResolution) {
    this.msResolution = msResolution;
  }

  public Boolean getShowTSUIDs() {
    return showTSUIDs;
  }

  public void setShowTSUIDs(Boolean showTSUIDs) {
    this.showTSUIDs = showTSUIDs;
  }

//  public Boolean getShowSummary() {
//    return showSummary;
//  }

//  public void setShowSummary(Boolean showSummary) {
//    this.showSummary = showSummary;
//  }

  public Boolean getShowStats() {
    return showStats;
  }

  public void setShowStats(Boolean showStats) {
    this.showStats = showStats;
  }

  public Boolean getShowQuery() {
    return showQuery;
  }

  public void setShowQuery(Boolean showQuery) {
    this.showQuery = showQuery;
  }

//  public Boolean getDelete() {
//    return delete;
//  }

//  public void setDelete(Boolean delete) {
//    this.delete = delete;
//  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public Boolean getUseCalendar() {
    return useCalendar;
  }

  public void setUseCalendar(Boolean useCalendar) {
    this.useCalendar = useCalendar;
  }

  public static QueryRequestBuilder builder() {
    return new QueryRequestBuilder();
  }

  public static final class QueryRequestBuilder {

    private String requestUUID;
    private String start;
    private String end;
    private List<Query> queries;
    private Boolean noAnnotations;
    private Boolean globalAnnotations;
    private Boolean msResolution;
    private Boolean showTSUIDs;
//    private Boolean showSummary;
    private Boolean showStats;
    private Boolean showQuery;
//    private Boolean delete;
    private String timezone;
    private Boolean useCalendar;

    private QueryRequestBuilder() {
    }

    public QueryRequestBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public QueryRequestBuilder start(String start) {
      this.start = start;
      return this;
    }

    public QueryRequestBuilder end(String end) {
      this.end = end;
      return this;
    }

    public QueryRequestBuilder queries(List<Query> queries) {
      this.queries = queries;
      return this;
    }

    public QueryRequestBuilder noAnnotations(Boolean noAnnotations) {
      this.noAnnotations = noAnnotations;
      return this;
    }

    public QueryRequestBuilder globalAnnotations(Boolean globalAnnotations) {
      this.globalAnnotations = globalAnnotations;
      return this;
    }

    public QueryRequestBuilder msResolution(Boolean msResolution) {
      this.msResolution = msResolution;
      return this;
    }

    public QueryRequestBuilder showTSUIDs(Boolean showTSUIDs) {
      this.showTSUIDs = showTSUIDs;
      return this;
    }

//    public QueryRequestBuilder showSummary(Boolean showSummary) {
//      this.showSummary = showSummary;
//      return this;
//    }

    public QueryRequestBuilder showStats(Boolean showStats) {
      this.showStats = showStats;
      return this;
    }

    public QueryRequestBuilder showQuery(Boolean showQuery) {
      this.showQuery = showQuery;
      return this;
    }

//    public QueryRequestBuilder delete(Boolean delete) {
//      this.delete = delete;
//      return this;
//    }

    public QueryRequestBuilder timezone(String timezone) {
      this.timezone = timezone;
      return this;
    }

    public QueryRequestBuilder useCalendar(Boolean useCalendar) {
      this.useCalendar = useCalendar;
      return this;
    }

    public QueryRequest build() {
      QueryRequest queryRequest = new QueryRequest();
      if (requestUUID != null) {
        queryRequest.setRequestUUID(requestUUID);
      }
      queryRequest.setStart(start);
      queryRequest.setEnd(end);
      queryRequest.setQueries(queries);
      queryRequest.setNoAnnotations(noAnnotations);
      queryRequest.setGlobalAnnotations(globalAnnotations);
      queryRequest.setMsResolution(msResolution);
      queryRequest.setShowTSUIDs(showTSUIDs);
//      queryRequest.setShowSummary(showSummary);
      queryRequest.setShowStats(showStats);
      queryRequest.setShowQuery(showQuery);
//      queryRequest.setDelete(delete);
      queryRequest.setTimezone(timezone);
      queryRequest.setUseCalendar(useCalendar);
      return queryRequest;
    }
  }
}
