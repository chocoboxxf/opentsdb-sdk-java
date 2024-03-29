package net.opentsdb.client;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.slf4j.Logger;

import net.opentsdb.client.api.Endpoint;
import net.opentsdb.client.api.annotation.request.CreateAnnotationRequest;
import net.opentsdb.client.api.annotation.request.DeleteAnnotationRequest;
import net.opentsdb.client.api.annotation.request.GetAnnotationRequest;
import net.opentsdb.client.api.annotation.request.UpdateAnnotationRequest;
import net.opentsdb.client.api.annotation.response.CreateAnnotationResponse;
import net.opentsdb.client.api.annotation.response.DeleteAnnotationResponse;
import net.opentsdb.client.api.annotation.response.GetAnnotationResponse;
import net.opentsdb.client.api.annotation.response.UpdateAnnotationResponse;
import net.opentsdb.client.api.delete.request.DeleteRequest;
import net.opentsdb.client.api.delete.response.DeleteResponse;
import net.opentsdb.client.api.put.request.PutRequest;
import net.opentsdb.client.api.put.response.PutResponse;
import net.opentsdb.client.api.query.request.QueryLastRequest;
import net.opentsdb.client.api.query.request.QueryRequest;
import net.opentsdb.client.api.query.response.QueryLastResponse;
import net.opentsdb.client.api.query.response.QueryResponse;
import net.opentsdb.client.api.suggest.request.SuggestRequest;
import net.opentsdb.client.api.suggest.response.SuggestResponse;
import net.opentsdb.client.api.uid.request.UIDAssignRequest;
import net.opentsdb.client.api.uid.response.UIDAssignResponse;
import net.opentsdb.client.bean.Annotation;
import net.opentsdb.client.bean.LastDataPoint;
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
   * /api/suggest
   * <br/><br/>
   *
   * This endpoint provides a means of implementing an "auto-complete" call
   * that can be accessed repeatedly as a user types a request in a GUI.
   * It does not offer full text searching or wildcards,
   * rather it simply matches the entire string passed in the query
   * on the first characters of the stored data.
   * For example, passing a query of type=metrics&q=sys
   * will return the top 25 metrics in the system that start with sys.
   * Matching is case sensitive, so sys will not match System.CPU.
   * Results are sorted alphabetically.
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/suggest.html">/api/suggest</a>
   *
   * @param request SuggestRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public SuggestResponse suggest(SuggestRequest request) throws IOException, URISyntaxException {
    HttpResponse response = httpClient.post(Endpoint.SUGGEST.getPath(), JsonUtil.writeValueAsString(request));
    List<String> results = HttpUtil.getResponse(response, List.class, String.class);
    return SuggestResponse.builder()
        .requestUUID(request.getRequestUUID())
        .results(results)
        .build();
  }

  /**
   * /api/query/last <br/><br/>
   *
   * This endpoint (2.1 and later) provides support for accessing the latest value of individual
   * time series. It provides an optimization over a regular query when only the last data point is
   * required. Locating the last point can be done with the timestamp of the meta data counter or by
   * scanning backwards from the current system time.
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/query/last.html">/api/suggest</a>
   *
   * @param request QueryLastRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public QueryLastResponse queryLast(QueryLastRequest request)
      throws IOException, URISyntaxException {
    HttpResponse response = httpClient
        .post(Endpoint.QUERY_LAST.getPath(), JsonUtil.writeValueAsString(request));
    List<LastDataPoint> results = HttpUtil.getResponse(response, List.class, LastDataPoint.class);
    return QueryLastResponse.builder()
        .requestUUID(request.getRequestUUID())
        .results(results)
        .build();
  }

  /**
   * /api/annotation <br/><br/>
   *
   * These endpoints provides a means of adding, editing or deleting annotations stored in the
   * OpenTSDB backend. Annotations are very basic objects used to record a note of an arbitrary
   * event at some point, optionally associated with a timeseries. Annotations are not meant to be
   * used as a tracking or event based system, rather they are useful for providing links to such
   * systems by displaying a notice on graphs or via API query calls. <br/> When creating, modifying
   * or deleting annotations, all changes will be propagated to the search plugin if configured.
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/annotation/index.html">/api/annotation</a>
   *
   * @param request CreateAnnotationRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public CreateAnnotationResponse createAnnotation(CreateAnnotationRequest request)
      throws IOException, URISyntaxException {
    HttpResponse response = httpClient
        .post(Endpoint.ANNOTATION.getPath(), JsonUtil.writeValueAsString(request));
    Annotation result = HttpUtil.getResponse(response, Annotation.class);
    return CreateAnnotationResponse.builder()
        .requestUUID(request.getRequestUUID())
        .result(result)
        .build();
  }

  /**
   * /api/annotation <br/><br/>
   *
   * These endpoints provides a means of adding, editing or deleting annotations stored in the
   * OpenTSDB backend. Annotations are very basic objects used to record a note of an arbitrary
   * event at some point, optionally associated with a timeseries. Annotations are not meant to be
   * used as a tracking or event based system, rather they are useful for providing links to such
   * systems by displaying a notice on graphs or via API query calls. <br/> When creating, modifying
   * or deleting annotations, all changes will be propagated to the search plugin if configured.
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/annotation/index.html">/api/annotation</a>
   *
   * @param request GetAnnotationRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public GetAnnotationResponse getAnnotation(GetAnnotationRequest request)
      throws IOException, URISyntaxException {
    HttpResponse response = httpClient
        .get(Endpoint.ANNOTATION.getPath(), request.getParameters());
    Annotation result = HttpUtil.getResponse(response, Annotation.class);
    return GetAnnotationResponse.builder()
        .requestUUID(request.getRequestUUID())
        .result(result)
        .build();
  }

  /**
   * /api/annotation <br/><br/>
   *
   * These endpoints provides a means of adding, editing or deleting annotations stored in the
   * OpenTSDB backend. Annotations are very basic objects used to record a note of an arbitrary
   * event at some point, optionally associated with a timeseries. Annotations are not meant to be
   * used as a tracking or event based system, rather they are useful for providing links to such
   * systems by displaying a notice on graphs or via API query calls. <br/> When creating, modifying
   * or deleting annotations, all changes will be propagated to the search plugin if configured.
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/annotation/index.html">/api/annotation</a>
   *
   * @param request UpdateAnnotationRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public UpdateAnnotationResponse updateAnnotation(UpdateAnnotationRequest request)
      throws IOException, URISyntaxException {
    HttpResponse response = httpClient
        .put(Endpoint.ANNOTATION.getPath(), JsonUtil.writeValueAsString(request));
    Annotation result = HttpUtil.getResponse(response, Annotation.class);
    return UpdateAnnotationResponse.builder()
        .requestUUID(request.getRequestUUID())
        .result(result)
        .build();
  }

  /**
   * /api/annotation <br/><br/>
   *
   * These endpoints provides a means of adding, editing or deleting annotations stored in the
   * OpenTSDB backend. Annotations are very basic objects used to record a note of an arbitrary
   * event at some point, optionally associated with a timeseries. Annotations are not meant to be
   * used as a tracking or event based system, rather they are useful for providing links to such
   * systems by displaying a notice on graphs or via API query calls. <br/> When creating, modifying
   * or deleting annotations, all changes will be propagated to the search plugin if configured.
   * @see <a href="http://opentsdb.net/docs/build/html/api_http/annotation/index.html">/api/annotation</a>
   *
   * @param request DeleteAnnotationRequest
   * @throws IOException
   * @throws URISyntaxException
   */
  public DeleteAnnotationResponse deleteAnnotation(DeleteAnnotationRequest request)
      throws IOException, URISyntaxException {
    HttpResponse response = httpClient
        .delete(Endpoint.ANNOTATION.getPath(), request.getParameters());
    DeleteAnnotationResponse result = HttpUtil.getResponse(response, DeleteAnnotationResponse.class);
    // empty response
    if (result == null) {
      result = DeleteAnnotationResponse.builder()
          .build();
    }
    result.setRequestUUID(request.getRequestUUID());
    return result;
  }


  /**
   * Close Client
   * @throws IOException
   */
  public void close() throws IOException {
    httpClient.close();
  }

}
