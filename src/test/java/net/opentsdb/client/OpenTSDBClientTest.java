package net.opentsdb.client;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import net.opentsdb.client.api.delete.request.DeleteRequest;
import net.opentsdb.client.api.delete.response.DeleteResponse;
import net.opentsdb.client.api.put.request.PutRequest;
import net.opentsdb.client.api.put.response.PutResponse;
import net.opentsdb.client.api.query.request.QueryRequest;
import net.opentsdb.client.api.query.response.QueryResponse;
import net.opentsdb.client.api.uid.request.UIDAssignRequest;
import net.opentsdb.client.api.uid.response.UIDAssignResponse;
import net.opentsdb.client.bean.Aggregator;
import net.opentsdb.client.bean.DataPoint;
import net.opentsdb.client.bean.Filter;
import net.opentsdb.client.bean.FilterType;
import net.opentsdb.client.bean.Query;

public class OpenTSDBClientTest {

  private OpenTSDBClient client;

  @Before
  public void setUp() {
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
    client = new OpenTSDBClient(config);
  }

  @Test
  public void testAssign() throws Exception {
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

    UIDAssignResponse response = client.uidAssign(request);
    System.out.println(response);
  }

  @Test
  public void testPut() throws Exception {
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

    PutResponse response = client.put(request);
    System.out.println(response);
  }

  @Test
  public void testQuery() throws Exception {
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

    QueryResponse response = client.query(request);
    System.out.println(response);
  }

  @Test
  public void testDelete() throws Exception {
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

    DeleteResponse response = client.delete(request);
    System.out.println(response);
  }

  @After
  public void tearDown() throws Exception {
    client.close();
  }
}