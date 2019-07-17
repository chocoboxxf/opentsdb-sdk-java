package net.opentsdb.client.api.annotation.response;

import net.opentsdb.client.api.BaseResponse;
import net.opentsdb.client.bean.Annotation;

/**
 * Base Annotation Response Object
 */
public class BaseAnnotationResponse extends BaseResponse {

  /**
   * result annotation
   */
  private Annotation result;

  public BaseAnnotationResponse() {
  }

  public Annotation getResult() {
    return result;
  }

  public void setResult(Annotation result) {
    this.result = result;
  }

}
