package net.opentsdb.client.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Monotonically increasing counter handling options
 */
public class RateOptions extends BaseBean {

  /**
   * Whether or not the underlying data is a monotonically increasing counter that may roll over
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("counter")
  private Boolean counter;

  /**
   * A positive integer representing the maximum value for the counter.
   * <br/>Default: {@code Long.MaxValue}
   * <br/>Example: 65535
   */
  @JsonProperty("counterMax")
  private Long counterMax;

  /**
   * An optional value that, when exceeded, 
   * will cause the aggregator to return a 0 instead of the calculated rate.
   * Useful when data sources are frequently reset to avoid spurious spikes.
   * <br/>Default: 0
   * <br/>Example: 65000
   */
  @JsonProperty("resetValue")
  private Long resetValue;

  /**
   * Whether or not to simply drop rolled-over or reset data points.
   * <br/>Default: false
   * <br/>Example: true
   */
  @JsonProperty("dropResets")
  private Boolean dropResets;

  public RateOptions() {
  }

  public Boolean getCounter() {
    return counter;
  }

  public void setCounter(Boolean counter) {
    this.counter = counter;
  }

  public Long getCounterMax() {
    return counterMax;
  }

  public void setCounterMax(Long counterMax) {
    this.counterMax = counterMax;
  }

  public Long getResetValue() {
    return resetValue;
  }

  public void setResetValue(Long resetValue) {
    this.resetValue = resetValue;
  }

  public Boolean getDropResets() {
    return dropResets;
  }

  public void setDropResets(Boolean dropResets) {
    this.dropResets = dropResets;
  }

  public static RateOptionsBuilder builder() {
    return new RateOptionsBuilder();
  }

  public static final class RateOptionsBuilder {

    private Boolean counter;
    private Long counterMax;
    private Long resetValue;
    private Boolean dropResets;

    private RateOptionsBuilder() {
    }

    public RateOptionsBuilder counter(Boolean counter) {
      this.counter = counter;
      return this;
    }

    public RateOptionsBuilder counterMax(Long counterMax) {
      this.counterMax = counterMax;
      return this;
    }

    public RateOptionsBuilder resetValue(Long resetValue) {
      this.resetValue = resetValue;
      return this;
    }

    public RateOptionsBuilder dropResets(Boolean dropResets) {
      this.dropResets = dropResets;
      return this;
    }

    public RateOptions build() {
      RateOptions rateOptions = new RateOptions();
      rateOptions.setCounter(counter);
      rateOptions.setCounterMax(counterMax);
      rateOptions.setResetValue(resetValue);
      rateOptions.setDropResets(dropResets);
      return rateOptions;
    }
  }
}
