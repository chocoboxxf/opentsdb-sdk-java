package net.opentsdb.client.api.put.request;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.opentsdb.client.api.BaseRequest;
import net.opentsdb.client.bean.DataPoint;

/**
 * Put Request Object
 */
public class PutRequest extends BaseRequest {

  /**
   * Whether or not to return summary information
   * <br/>Default: false
   * <br/>Example: /api/put?summary
   */
  private Boolean summary;

  /**
   * Whether or not to return detailed information
   * <br/>Default: false
   * <br/>Example: /api/put?details
   */
  private Boolean details;

  /**
   * Whether or not to wait for the data to be flushed to storage before returning the results.
   * <br/>Default: false
   * <br/>Example: /api/put?sync
   */
  private Boolean sync;

  /**
   * A timeout, in milliseconds, to wait for the data to be flushed to storage
   * before returning with an error. When a timeout occurs, 
   * using the {@code details} flag will tell how many data points failed and how many succeeded.
   * {@code sync} must also be given for this to take effect.
   * A value of 0 means the write will not timeout.
   * <br/>Default: false
   * <br/>Example: /api/put/?sync&sync_timeout=60000
   */
  private Long syncTimeout;

  /**
   * data point list in BODY
   */
  private List<DataPoint> dataPoints;

  public PutRequest() {
  }

  public Boolean getSummary() {
    return summary;
  }

  public void setSummary(Boolean summary) {
    this.summary = summary;
  }

  public Boolean getDetails() {
    return details;
  }

  public void setDetails(Boolean details) {
    this.details = details;
  }

  public Boolean getSync() {
    return sync;
  }

  public void setSync(Boolean sync) {
    this.sync = sync;
  }

  public Long getSyncTimeout() {
    return syncTimeout;
  }

  public void setSyncTimeout(Long syncTimeout) {
    this.syncTimeout = syncTimeout;
  }

  public List<DataPoint> getDataPoints() {
    return dataPoints;
  }

  public void setDataPoints(List<DataPoint> dataPoints) {
    this.dataPoints = dataPoints;
  }

  public Map<String, String> getParameters() {
    Map<String, String> parameters = new LinkedHashMap<>();
    if (summary != null && summary) {
      parameters.put("summary", "");
    }
    if (details != null && details) {
      parameters.put("details", "");
    }
    if (sync != null && sync) {
      parameters.put("sync", "");
    }
    if (sync != null && sync && syncTimeout != null && syncTimeout > 0) {
      parameters.put("sync_timeout", syncTimeout.toString());
    }
    return parameters;
  }

  public static PutRequestBuilder builder() {
    return new PutRequestBuilder();
  }

  public static final class PutRequestBuilder {

    private String requestUUID;
    private Boolean summary;
    private Boolean details;
    private Boolean sync;
    private Long syncTimeout;
    private List<DataPoint> dataPoints;

    private PutRequestBuilder() {
    }

    public PutRequestBuilder requestUUID(String requestUUID) {
      this.requestUUID = requestUUID;
      return this;
    }

    public PutRequestBuilder summary(Boolean summary) {
      this.summary = summary;
      return this;
    }

    public PutRequestBuilder details(Boolean details) {
      this.details = details;
      return this;
    }

    public PutRequestBuilder sync(Boolean sync) {
      this.sync = sync;
      return this;
    }

    public PutRequestBuilder syncTimeout(Long syncTimeout) {
      this.syncTimeout = syncTimeout;
      return this;
    }

    public PutRequestBuilder dataPoints(List<DataPoint> dataPoints) {
      this.dataPoints = dataPoints;
      return this;
    }

    public PutRequest build() {
      PutRequest putRequest = new PutRequest();
      if (requestUUID != null) {
        putRequest.setRequestUUID(requestUUID);
      }
      putRequest.setSummary(summary);
      putRequest.setDetails(details);
      putRequest.setSync(sync);
      putRequest.setSyncTimeout(syncTimeout);
      putRequest.setDataPoints(dataPoints);
      return putRequest;
    }
  }
}
