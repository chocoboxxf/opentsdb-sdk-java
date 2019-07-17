package net.opentsdb.client.api;

/**
 * API Endpoint
 *
 * @see <a href="http://opentsdb.net/docs/build/html/api_http/index.html#api-endpoints">All API
 * Endpoints</>
 */
public enum Endpoint {
  ANNOTATION("/api/annotation"), // Annotation CRUD
  PUT("/api/put"), // Write Data
  QUERY("/api/query"), // Query Data
  QUERY_LAST("/api/query/last"), // Query latest data
  SUGGEST("/api/suggest"), // Auto complete Metrics/Tag Key/Tag Value names
  UID_ASSIGN("/api/uid/assign"), // Create Metrics/Tag Key/Tag Value 
  ;
  /**
   * API Endpoint Path
   */
  private String path;

  Endpoint(String path) {
    this.path = path;
  }

  public String getPath() {
    return path;
  }
}
