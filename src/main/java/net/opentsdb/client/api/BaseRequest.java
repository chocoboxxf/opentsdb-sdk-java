package net.opentsdb.client.api;

import java.util.UUID;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import net.opentsdb.client.bean.BaseBean;

/**
 * Base class for API request
 */
public abstract class BaseRequest extends BaseBean {

  /**
   * Request UUID, for tracking 
   */
  @JsonIgnore
  private String requestUUID;

  public String getRequestUUID() {
    return requestUUID;
  }

  public void setRequestUUID(String requestUUID) {
    this.requestUUID = requestUUID;
  }

  /**
   * Generate random UUID in default, can be overwritten by setter
   */
  public BaseRequest() {
    requestUUID = UUID.randomUUID().toString();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
