package net.opentsdb.client.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Filter objects for querying or deleting request
 */
public class Filter extends BaseBean {

  /**
   * The name of the filter to invoke.
   * <br/>Example: regexp
   * @see <a href="http://opentsdb.net/docs/build/html/user_guide/query/filters.html">Query Filters</a>
   */
  @JsonProperty(value = "type", required = true)
  private FilterType type;

  /**
   * The tag key to invoke the filter on
   * <br/>Example: host
   */
  @JsonProperty(value = "tagk", required = true)
  private String tagk;

  /**
   * The filter expression to evaluate and depends on the filter being used
   * <br/>Example: web.*.mysite.com
   */
  @JsonProperty(value = "filter", required = true)
  private String filter;

  /**
   * Whether or not to group the results by each value matched by the filter.
   * By default all values matching the filter will be aggregated into a single series.
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("groupBy")
  private Boolean groupBy;

  public Filter() {
  }

  public FilterType getType() {
    return type;
  }

  public void setType(FilterType type) {
    this.type = type;
  }

  public String getTagk() {
    return tagk;
  }

  public void setTagk(String tagk) {
    this.tagk = tagk;
  }

  public String getFilter() {
    return filter;
  }

  public void setFilter(String filter) {
    this.filter = filter;
  }

  public Boolean getGroupBy() {
    return groupBy;
  }

  public void setGroupBy(Boolean groupBy) {
    this.groupBy = groupBy;
  }

  public static FilterBuilder builder() {
    return new FilterBuilder();
  }


  public static final class FilterBuilder {

    private FilterType type;
    private String tagk;
    private String filter;
    private Boolean groupBy;

    private FilterBuilder() {
    }

    public FilterBuilder type(FilterType type) {
      this.type = type;
      return this;
    }

    public FilterBuilder tagk(String tagk) {
      this.tagk = tagk;
      return this;
    }

    public FilterBuilder filter(String filter) {
      this.filter = filter;
      return this;
    }

    public FilterBuilder groupBy(Boolean groupBy) {
      this.groupBy = groupBy;
      return this;
    }

    public Filter build() {
      Filter filterObject = new Filter();
      filterObject.setType(type);
      filterObject.setTagk(tagk);
      filterObject.setFilter(filter);
      filterObject.setGroupBy(groupBy);
      return filterObject;
    }
  }
}
