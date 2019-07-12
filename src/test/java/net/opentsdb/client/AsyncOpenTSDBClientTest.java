package net.opentsdb.client;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.opentsdb.client.api.delete.callback.DeleteCallback;
import net.opentsdb.client.api.delete.request.DeleteRequest;
import net.opentsdb.client.api.delete.response.DeleteResponse;
import net.opentsdb.client.api.put.callback.PutCallback;
import net.opentsdb.client.api.put.request.PutRequest;
import net.opentsdb.client.api.put.response.PutResponse;
import net.opentsdb.client.api.query.callback.QueryCallback;
import net.opentsdb.client.api.query.request.QueryRequest;
import net.opentsdb.client.api.query.response.QueryResponse;
import net.opentsdb.client.api.suggest.callback.SuggestCallback;
import net.opentsdb.client.api.suggest.request.SuggestRequest;
import net.opentsdb.client.api.suggest.response.SuggestResponse;
import net.opentsdb.client.api.uid.callback.UIDAssignCallback;
import net.opentsdb.client.api.uid.request.UIDAssignRequest;
import net.opentsdb.client.api.uid.response.UIDAssignResponse;
import net.opentsdb.client.bean.Aggregator;
import net.opentsdb.client.bean.DataPoint;
import net.opentsdb.client.bean.Filter;
import net.opentsdb.client.bean.FilterType;
import net.opentsdb.client.bean.Query;
import net.opentsdb.client.bean.SuggestType;
import net.opentsdb.client.exception.ErrorException;

public class AsyncOpenTSDBClientTest {
  
  private AsyncOpenTSDBClient client;
  
  @Before
  public void setUp() throws Exception {
    OpenTSDBConfig config = OpenTSDBConfig.builder()
        .host("127.0.0.1")
        .port(4242)
        .socketTimeout(10)
        .connectTimeout(10)
        .connectionRequestTimeout(10)
        .maxConnections(100)
        .maxPerRoute(100)
        .readOnly(false)
        .build();
    client = new AsyncOpenTSDBClient(config);
  }
  
  @Test
  public void testUIDAssign() throws Exception {
    UIDAssignCallback callback = new UIDAssignCallback() {
      @Override
      public void response(UIDAssignResponse response) {
        System.out.println("response");
        System.out.println(response);
      }

      @Override
      public void responseError(ErrorException ee) {
        System.out.println("response error");
        System.out.println(ee.getMessage());
      }

      @Override
      public void failed(Exception e) {
        System.out.println("failed");
        System.out.println(e.getMessage());
      }
    };

    List<String> metrics = new LinkedList<>();
    metrics.add("cpu.0.idle");
    metrics.add("cpu.1.idle");

    List<String> tagks = new LinkedList<>();
    tagks.add("dc");
    tagks.add("host");

    List<String> tagvs = new LinkedList<>();
    tagvs.add("sh");
    tagvs.add("bj");
    tagvs.add("host001");
    tagvs.add("host002");

    UIDAssignRequest request = UIDAssignRequest.builder()
        .metric(metrics)
        .tagk(tagks)
        .tagv(tagvs)
        .build();

    client.uidAssign(request, callback);
  }

  @Test
  public void testPut() throws Exception {
    PutCallback callback = new PutCallback() {
      @Override
      public void response(PutResponse response) {
        System.out.println("response");
        System.out.println(response);
      }

      @Override
      public void responseError(ErrorException ee) {
        System.out.println("response error");
        System.out.println(ee.getMessage());
      }

      @Override
      public void failed(Exception e) {
        System.out.println("failed");
        System.out.println(e.getMessage());
      }
    };

    Map<String, String> tags = new LinkedHashMap<>();
    tags.put("dc", "sh");
    tags.put("host", "host001");

    Long currentTime = System.currentTimeMillis();

    List<DataPoint> dataPoints = new LinkedList<>();
    dataPoints.add(
        DataPoint.builder()
            .metric("cpu.0.idle")
            .tags(tags)
            .timestamp(currentTime)
            .value(0.8)
            .build()
    );
    dataPoints.add(
        DataPoint.builder()
            .metric("cpu.1.idle")
            .tags(tags)
            .timestamp(currentTime)
            .value(0.75)
            .build()
    );

    PutRequest request = PutRequest.builder()
        .summary(true)
        .details(true)
        .dataPoints(dataPoints)
        .build();

    client.put(request, callback);
  }
  
  @Test
  public void testQuery() throws Exception {
    QueryCallback callback = new QueryCallback() {
      @Override
      public void response(QueryResponse response) {
        System.out.println("response");
        System.out.println(response);
      }

      @Override
      public void responseError(ErrorException ee) {
        System.out.println("response error");
        System.out.println(ee.getMessage());
      }

      @Override
      public void failed(Exception e) {
        System.out.println("failed");
        System.out.println(e.getMessage());
      }
    };
    List<Filter> filters = new LinkedList<>();
    filters.add(
        Filter.builder()
            .type(FilterType.LITERAL_OR)
            .tagk("dc")
            .filter("bj|sh")
            .build()
    );
    filters.add(
        Filter.builder()
            .type(FilterType.REGEXP)
            .tagk("host")
            .filter("host.*")
            .build()
    );

    List<Query> queries = new LinkedList<>();
    queries.add(
        Query.builder()
            .aggregator(Aggregator.MIN)
            .metric("cpu.0.idle")
            .downsample("1s-min")
            .filters(filters)
            .build()
    );
    queries.add(
        Query.builder()
            .aggregator(Aggregator.AVG)
            .metric("cpu.0.idle")
            .downsample("1s-avg")
            .filters(filters)
            .build()
    );

    QueryRequest request = QueryRequest.builder()
        .start("1d-ago")
        .end("1s-ago")
        .queries(queries)
        .build();
    
    client.query(request, callback);
  }
  
  @Test
  public void testDelete() throws Exception {
    DeleteCallback callback = new DeleteCallback() {
      @Override
      public void response(DeleteResponse response) {
        System.out.println("response");
        System.out.println(response);
      }

      @Override
      public void responseError(ErrorException ee) {
        System.out.println("response error");
        System.out.println(ee.getMessage());
      }

      @Override
      public void failed(Exception e) {
        System.out.println("failed");
        System.out.println(e.getMessage());
      }
    };

    List<Filter> filters = new LinkedList<>();
    filters.add(
        Filter.builder()
            .type(FilterType.LITERAL_OR)
            .tagk("dc")
            .filter("bj|sh")
            .build()
    );

    List<Query> queries = new LinkedList<>();
    queries.add(
        Query.builder()
            .aggregator(Aggregator.NONE)
            .metric("cpu.1.idle")
            .filters(filters)
            .build()
    );

    DeleteRequest request = DeleteRequest.builder()
        .start("1y-ago")
        .end("1s-ago")
        .queries(queries)
        .build();

    client.delete(request, callback);
  }

  @Test
  public void testSuggest() throws Exception {
    SuggestCallback callback = new SuggestCallback() {
      @Override
      public void response(SuggestResponse response) {
        System.out.println("response");
        System.out.println(response);
      }

      @Override
      public void responseError(ErrorException ee) {
        System.out.println("response error");
        System.out.println(ee.getMessage());
      }

      @Override
      public void failed(Exception e) {
        System.out.println("failed");
        System.out.println(e.getMessage());
      }
    };
    SuggestRequest request = SuggestRequest.builder()
        .type(SuggestType.METRICS)
        .q("cpu")
        .max(100)
        .build();

    client.suggest(request, callback);
  }

  @After
  public void tearDown() throws Exception {
    client.close();
  }
}