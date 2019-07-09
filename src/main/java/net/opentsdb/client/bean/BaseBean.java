package net.opentsdb.client.bean;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Base class for beans
 */
public class BaseBean implements Serializable {

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
  
}
