package net.opentsdb.client.api;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.opentsdb.client.bean.BaseBean;

/**
 * Base class for API response
 */
public class BaseResponse  extends BaseBean {

  /**
   * Request UUID, for tracking, should be the same as the request
   */
  @JsonIgnore
  private String requestUUID;

  public String getRequestUUID() {
    return requestUUID;
  }

  public void setRequestUUID(String requestUUID) {
    this.requestUUID = requestUUID;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
