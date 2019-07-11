package net.opentsdb.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;

import net.opentsdb.client.api.Endpoint;
import net.opentsdb.client.api.delete.request.DeleteRequest;
import net.opentsdb.client.api.delete.response.DeleteResponse;
import net.opentsdb.client.api.put.request.PutRequest;
import net.opentsdb.client.api.put.response.PutResponse;
import net.opentsdb.client.api.query.request.QueryRequest;
import net.opentsdb.client.api.query.response.QueryResponse;
import net.opentsdb.client.api.uid.request.UIDAssignRequest;
import net.opentsdb.client.api.uid.response.UIDAssignResponse;
import net.opentsdb.client.bean.QueryResult;
import net.opentsdb.client.exception.ReadOnlyException;
import net.opentsdb.client.http.HttpClient;
import net.opentsdb.client.http.HttpClientFactory;
import net.opentsdb.client.util.HttpUtil;
import net.opentsdb.client.util.JsonUtil;

/**
 * OpenTSDB client
 */
public class OpenTSDBClient {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(OpenTSDBClient.class);

  private final OpenTSDBConfig config;

  private final HttpClient httpClient;

  public OpenTSDBClient(OpenTSDBConfig config) {
    this.config = config;
    this.httpClient = HttpClientFactory.getInstance()
        .getHttpClient(this.config);

    log.debug("the http client has started");
  }

  /**
   * /api/uid/assign
   * <br/><br/>
   * This endpoint enables assigning UIDs to new metrics, tag names and tag values.
   * Multiple types and names can be provided in a single call
   * and the API will process each name individually, 
   * reporting which names were assigned UIDs successfully, along with the UID assigned,
   * and which failed due to invalid characters or had already been assigned.
   * Assignment can be performed via query string or content data.
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/uid/assign.html">/api/uid/assign</a>
   *
   * @param request UIDAssignRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public UIDAssignResponse uidAssign(UIDAssignRequest request)
      throws IOException, URISyntaxException {
    if (config.isReadOnly()) {
      throw new ReadOnlyException();
    }
    HttpResponse response = httpClient
        .post(Endpoint.UID_ASSIGN.getPath(), JsonUtil.writeValueAsString(request));
    UIDAssignResponse result = HttpUtil.getResponse(response, UIDAssignResponse.class); 
    result.setRequestUUID(request.getRequestUUID());
    return result;
  }

  /**
   * /api/put
   * <br/><br/>
   * This endpoint allows for storing data in OpenTSDB over HTTP
   * as an alternative to the Telnet interface.
   * Put requests can only be performed via content associated with the POST method.
   * The format of the content is dependent on the serializer selected.
   * However there are some common parameters and responses as documented below.
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/put.html">/api/put</a>
   *
   * @param request PutRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public PutResponse put(PutRequest request) throws IOException, URISyntaxException {
    if (config.isReadOnly()) {
      throw new ReadOnlyException();
    }
    HttpResponse response = httpClient.post(
        Endpoint.PUT.getPath(), JsonUtil.writeValueAsString(request.getDataPoints()), request.getParameters()
    );
    PutResponse result = HttpUtil.getResponse(response, PutResponse.class);
    // empty response
    if (result == null) {
      result = PutResponse.builder()
          .success(request.getDataPoints().size())
          .failed(0)
          .errors(new LinkedList<>())
          .build();
    }
    result.setRequestUUID(request.getRequestUUID());
    return result;
  }

  /**
   * /api/query
   * <br/><br/>
   * Probably the most useful endpoint in the API,
   * /api/query enables extracting data from the storage system in various formats
   * determined by the serializer selected.
   * Queries can be submitted via the 1.0 query string format or body content.
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/query/index.html">/api/query</a>
   *
   * @param request QueryRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public QueryResponse query(QueryRequest request) throws IOException, URISyntaxException {
    HttpResponse response = httpClient.post(Endpoint.QUERY.getPath(), JsonUtil.writeValueAsString(request));
    List<QueryResult> results = HttpUtil.getResponse(response, List.class, QueryResult.class);
    return QueryResponse.builder()
        .requestUUID(request.getRequestUUID())
        .results(results)
        .build();
  }

  /**
   * /api/query
   * <br/><br/>
   * As of 2.2 data matching a query can be deleted by using the DELETE verb.
   * The configuration parameter {@code tsd.http.query.allow_delete} must be enabled to allow deletions.
   * Data that is deleted will be returned in the query results.
   * Executing the query a second time should return empty results.
   * <br/><br/>
   * WARNING:
   * Deleting data is permanent. Also beware that when deleting, 
   * some data outside the boundaries of the start and end times may be deleted
   * as data is stored on an hourly basis.
   *
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/query/index.html">/api/query</a>
   *
   * @param request DeleteRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public DeleteResponse delete(DeleteRequest request) throws IOException, URISyntaxException {
    if (config.isReadOnly()) {
      throw new ReadOnlyException();
    }
    HttpResponse response = httpClient.post(Endpoint.QUERY.getPath(), JsonUtil.writeValueAsString(request));
    List<QueryResult> results = HttpUtil.getResponse(response, List.class, QueryResult.class);
    return DeleteResponse.builder()
        .requestUUID(request.getRequestUUID())
        .results(results)
        .build();
  }

  /**
   * Close Client
   * @throws IOException
   */
  public void close() throws IOException {
    httpClient.close();
  }

}
