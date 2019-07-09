package net.opentsdb.client;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.nio.reactor.IOReactorException;
import org.slf4j.Logger;

import net.opentsdb.client.api.Endpoint;
import net.opentsdb.client.api.delete.callback.DeleteCallback;
import net.opentsdb.client.api.delete.callback.DeleteFutureCallback;
import net.opentsdb.client.api.delete.request.DeleteRequest;
import net.opentsdb.client.api.put.callback.PutCallback;
import net.opentsdb.client.api.put.callback.PutFutureCallback;
import net.opentsdb.client.api.put.request.PutRequest;
import net.opentsdb.client.api.query.callback.QueryCallback;
import net.opentsdb.client.api.query.callback.QueryFutureCallback;
import net.opentsdb.client.api.query.request.QueryRequest;
import net.opentsdb.client.api.uid.callback.UIDAssignCallback;
import net.opentsdb.client.api.uid.callback.UIDAssignFutureCallback;
import net.opentsdb.client.api.uid.request.UIDAssignRequest;
import net.opentsdb.client.exception.ReadOnlyException;
import net.opentsdb.client.http.AsyncHttpClient;
import net.opentsdb.client.http.HttpClientFactory;
import net.opentsdb.client.util.JsonUtil;

/**
 * Async OpenTSDB client
 */
public class AsyncOpenTSDBClient {

  private static final Logger log = org.slf4j.LoggerFactory.getLogger(AsyncOpenTSDBClient.class);

  private final OpenTSDBConfig config;

  private final AsyncHttpClient httpClient;

  public AsyncOpenTSDBClient(OpenTSDBConfig config) throws IOReactorException {
    this.config = config;
    this.httpClient = HttpClientFactory.getInstance()
        .getAsyncHttpClient(this.config);

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
   * @param callback UIDAssignCallback
   * @throws IOException
   * @throws URISyntaxException
   */
  public void uidAssign(UIDAssignRequest request, UIDAssignCallback callback)
      throws IOException, URISyntaxException {
    if (config.isReadOnly()) {
      throw new ReadOnlyException();
    }
    httpClient.post(
        Endpoint.UID_ASSIGN.getPath(),
        JsonUtil.writeValueAsString(request),
        new UIDAssignFutureCallback(request, callback)
    );
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
   * @param callback PutCallback
   * @throws IOException
   * @throws URISyntaxException
   */
  public void put(PutRequest request, PutCallback callback) throws IOException, URISyntaxException {
    if (config.isReadOnly()) {
      throw new ReadOnlyException();
    }
    httpClient.post(
        Endpoint.PUT.getPath(),
        JsonUtil.writeValueAsString(request.getDataPoints()),
        request.getParameters(),
        new PutFutureCallback(request, callback)
    );
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
   * @param callback QueryCallback
   * @throws IOException
   * @throws URISyntaxException
   */
  public void query(QueryRequest request, QueryCallback callback)
      throws IOException, URISyntaxException {
    httpClient.post(
        Endpoint.QUERY.getPath(),
        JsonUtil.writeValueAsString(request),
        new QueryFutureCallback(request, callback)
    );
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
   * @param callback DeleteCallback
   * @throws IOException
   * @throws URISyntaxException
   */
  public void delete(DeleteRequest request, DeleteCallback callback)
      throws IOException, URISyntaxException {
    if (config.isReadOnly()) {
      throw new ReadOnlyException();
    }
    httpClient.post(
        Endpoint.QUERY.getPath(),
        JsonUtil.writeValueAsString(request),
        new DeleteFutureCallback(request, callback)
    );
  }

  /**
   * Gracefully Close Client
   * @throws IOException
   */
  public void close() throws IOException {
    httpClient.close(false);
  }

}