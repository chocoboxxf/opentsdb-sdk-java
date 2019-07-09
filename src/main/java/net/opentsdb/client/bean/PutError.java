package net.opentsdb.client.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Detail of data points failed to be put. 
 */
public class PutError extends BaseBean {

  /**
   * Data point
   */
  @JsonProperty("datapoint")
  private DataPoint dataPoint;

  /**
   * Detail message of error
   * <br/>Example: Unable to parse value to a number
   */
  @JsonProperty("error")
  private String error;

  public PutError() {
  }

  public DataPoint getDataPoint() {
    return dataPoint;
  }

  public void setDataPoint(DataPoint dataPoint) {
    this.dataPoint = dataPoint;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }

  public static PutErrorBuilder builder() {
    return new PutErrorBuilder();
  }
  
  public static final class PutErrorBuilder {

    private DataPoint dataPoint;
    private String error;

    private PutErrorBuilder() {
    }

    public PutErrorBuilder dataPoint(DataPoint dataPoint) {
      this.dataPoint = dataPoint;
      return this;
    }

    public PutErrorBuilder error(String error) {
      this.error = error;
      return this;
    }

    public PutError build() {
      PutError putError = new PutError();
      putError.setDataPoint(dataPoint);
      putError.setError(error);
      return putError;
    }
  }
}
