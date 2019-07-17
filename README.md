# OpenTSDB SDK for Java

- Release Version: 0.2.0
- Snapshot Version: 0.2.1-SNAPSHOT (Not Stable)

## Index

1. [Requirements](#requirements)
2. [Install](#install)
    1. [From maven](#from-maven)
    2. [From local build](#from-local-build)
3. [Quickstart(Sync)](#quickstartsync)
    1. [Create Connection](#create-connection)
    2. [Create Metrics / Tag Keys / Tag Values](#create-metrics--tag-keys--tag-values)
    3. [Put Data](#put-data)
    4. [Query Data](#query-data)
    5. [Query Last Data Point](#query-last-data-point)
    6. [Delete Data](#delete-data) 
    7. [Suggest](#suggest) 
    8. [Annotation](#annotation)
        1. [Create Annotation](#create-annotation)
        2. [Get Annotation](#get-annotation)
        3. [Update Annotation](#update-annotation)
        4. [Delete Annotation](#delete-annotation)
4. [Quickstart(Async)](#quickstartasync)
    1. [Create Connection](#create-connection-1)
    2. [Create Metrics / Tag Keys / Tag Values](#create-metrics--tag-keys--tag-values-1)
    3. [Put Data](#put-data-1)
    4. [Query Data](#query-data-1) 
    5. [Query Last Data Point](#query-last-data-point-1)
    6. [Delete Data](#delete-data-1) 
    7. [Suggest](#suggest-1) 
    8. [Annotation](#annotation-1)
        1. [Create Annotation](#create-annotation-1)
        2. [Get Annotation](#get-annotation-1)
        3. [Update Annotation](#update-annotation-1)
        4. [Delete Annotation](#delete-annotation-1)
5. [Authors](#authors)
6. [License](#license)

## Requirements

- Java 1.8 or later
- Maven

## Install

### From maven

1. add release version dependency to pom.xml

```xml
<dependency>
  <groupId>com.github.chocoboxxf</groupId>
  <artifactId>opentsdb-sdk-java</artifactId>
  <version>0.2.0</version>
</dependency> 
```

### From local build

1. check out the code

```bash
git clone https://github.com/chocoboxxf/opentsdb-sdk-java.git
```

2. build the package

```bash
mvn clean package -DskipTests
```

3. install snapshot version to local repo or run `mvn  install` directly

```bash
mvn install:install-file \
-Dfile=`PWD`/target/opentsdb-sdk-java-0.2.1-SNAPSHOT.jar \
-DgroupId=com.github.chocoboxxf \
-DartifactId=opentsdb-sdk-java \
-Dversion=0.2.1-SNAPSHOT \
-Dpackaging=jar \
-DpomFile=`PWD`/pom.xml
```

4. add snapshot version dependency to pom.xml

```xml
<dependency>
  <groupId>com.github.chocoboxxf</groupId>
  <artifactId>opentsdb-sdk-java</artifactId>
  <version>0.2.1-SNAPSHOT</version>
</dependency> 
```

## Quickstart(Sync)

### Create Connection

```java
// parameters can be loaded from configuration file
OpenTSDBConfig config = OpenTSDBConfig.builder()
    .host("127.0.0.1")
    .port(4242)
    .socketTimeout(10)
    .connectTimeout(10)
    .connectionRequestTimeout(10)
    .maxConnections(100)
    .maxPerRoute(100)
    .readOnly(false) // if set to true, all methods to write data will throw ReadOnlyException
    .build();
OpenTSDBClient client = new OpenTSDBClient(config);

// ... using client

client.close();
```

### Create Metrics / Tag Keys / Tag Values

* Example 1

```java
// metrics to be created
List<String> metrics = new LinkedList<>();
metrics.add("cpu.0.idle");
metrics.add("cpu.1.idle");

UIDAssignRequest request = UIDAssignRequest.builder()
    .metric(metrics)
    .build();

UIDAssignResponse response = client.uidAssign(request);
```

* Example 2

```java
// tag keys to be created
List<String> tagks = new LinkedList<>();
tagks.add("dc");
tagks.add("host");

UIDAssignRequest request = UIDAssignRequest.builder()
    .tagk(tagks)
    .build();

UIDAssignResponse response = client.uidAssign(request);
```

Example 3

```java
// tag values to be created
List<String> tagvs = new LinkedList<>();
tagvs.add("sh");
tagvs.add("bj");
tagvs.add("host001");
tagvs.add("host002");

UIDAssignRequest request = UIDAssignRequest.builder()
    .tagv(tagvs)
    .build();

UIDAssignResponse response = client.uidAssign(request);
```

### Put Data

```java
// tags for data points
Map<String, String> tags = new LinkedHashMap<>();
tags.put("dc", "sh");
tags.put("host", "host001");

// timestamp for data points
// accept second or millisecond 
Long currentTime = System.currentTimeMillis();

// data points
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
    .summary(true) // show success and failed number in response
    .details(true) // show detailed erros in response
    .dataPoints(dataPoints)
    .build();

PutResponse response = client.put(request);
```


### Query Data

```java
/**
 * s - Seconds
 * m - Minutes
 * h - Hours
 * d - Days (24 hours)
 * w - Weeks (7 days)
 * n - Months (30 days)
 * y - Years (365 days)
 */

// query filters
List<Filter> filters = new LinkedList<>();
// WHERE dc IN ('bj', 'sh') 
filters.add(
    Filter.builder()
        .type(FilterType.LITERAL_OR)
        .tagk("dc")
        .filter("bj|sh")
        .build()
);
// AND host REGEXP 'host.*'
filters.add(
    Filter.builder()
        .type(FilterType.REGEXP)
        .tagk("host")
        .filter("host.*")
        .build()
);
    
// queries
List<Query> queries = new LinkedList<>();
queries.add(
    Query.builder()
        .aggregator(Aggregator.MIN)
        .metric("cpu.0.idle")
        /**
         * While OpenTSDB can store data with millisecond resolution 
         * by default, queries will return the data with second resolution
         * to provide backwards compatibility for existing tools.
         * If you are storing multiple data points per second,
         * make sure that any query you issue includes a 1s-<func> downsampler
         * to read the right data. 
         * Otherwise an indeterminate value will be emitted.
         */
        .downsample("1s-min") // minimum value in 1 second
        .filters(filters)
        .build()
);
queries.add(
    Query.builder()
        .aggregator(Aggregator.AVG)
        .metric("cpu.0.idle")
        .downsample("1s-avg") // average value in 1 second
        .filters(filters)
        .build()
);

QueryRequest request = QueryRequest.builder()
    .start("1d-ago") // start from 1 day ago
    .end("1h-ago") // end at 1 hour ago
    .queries(queries)
    .build();

QueryResponse response = client.query(request);
```

### Query Last Data Point

```java
Map<String, String> tags = new LinkedHashMap<>();
tags.put("dc", "sh");
tags.put("host", "host001");

List<String> tsuids = new LinkedList<>();
tsuids.add("000001000001000001");

List<LastDataPointQuery> queries = new LinkedList<>();
// query by metric and tags
queries.add(
    LastDataPointQuery.builder()
        .metric("cpu.0.idle")
        .tags(tags)
        .build()
);
// query by tsuids
queries.add(
    LastDataPointQuery.builder()
        .tsuids(tsuids)
        .build()
);

QueryLastRequest request = QueryLastRequest.builder()
    .queries(queries)
    .resolveNames(true)
    .backScan(0)
    .build();

QueryLastResponse response = client.queryLast(request);
```

### Delete Data

```java
// query filters
List<Filter> filters = new LinkedList<>();
// WHERE dc IN ('bj', 'sh') 
filters.add(
    Filter.builder()
        .type(FilterType.LITERAL_OR)
        .tagk("dc")
        .filter("bj|sh")
        .build()
);

// queries
List<Query> queries = new LinkedList<>();
queries.add(
    Query.builder()
        .aggregator(Aggregator.NONE) // required parameter by opentsdb, set to anything
        .metric("cpu.1.idle")
        .filters(filters)
        .build()
);

// delete by queries
DeleteRequest request = DeleteRequest.builder()
    .start("2y-ago") // start from 2 years ago
    .end("1y-ago") // end at 1 year ago
    .queries(queries)
    .build();

DeleteResponse response = client.delete(request);
```

### Suggest

```java
// search metrics/tag key/tag value by `q` (keyword)
SuggestRequest request = SuggestRequest.builder()
    .type(SuggestType.METRICS)
    .q("cpu")
    .max(100)
    .build();

SuggestResponse response = client.suggest(request);
```

### Annotation

#### Create Annotation

```java
Map<String, String> custom = new LinkedHashMap<>();
custom.put("priority", "T0");
custom.put("operator", "user1");

CreateAnnotationRequest request = CreateAnnotationRequest.builder()
    .startTime(1369141261)
    .endTime(1369141262) // null if not completed
    .tsuid("000001000001000001") // null for global event
    .description("Network Outage")
    .notes("Switch #5 died and was replaced")
    .custom(custom)
    .build();

CreateAnnotationResponse response = client.createAnnotation(request);
```

#### Get Annotation

```java
GetAnnotationRequest request = GetAnnotationRequest.builder()
    .startTime(1369141261)
    .endTime(1369141262) // not neccesarily
    .tsuid("000001000001000001")
    .build();

GetAnnotationResponse response = client.getAnnotation(request);
```

#### Update Annotation

```java
Map<String, String> custom = new LinkedHashMap<>();
custom.put("priority", "T1");
custom.put("operator", "user2");

UpdateAnnotationRequest request = UpdateAnnotationRequest.builder()
    .startTime(1369141261)
    .endTime(1369141264)
    .tsuid("000001000001000001")
    .description("Network Outage")
    .notes("Switch #5 and #6 died and were replaced")
    .custom(custom)
    .build();

UpdateAnnotationResponse response = client.updateAnnotation(request);
```

#### Delete Annotation

```java
DeleteAnnotationRequest request = DeleteAnnotationRequest.builder()
    .startTime(1369141261)
    .endTime(1369141262)
    .tsuid("000001000001000001")
    .build();

// nothing in response when succeeded
DeleteAnnotationResponse response = client.deleteAnnotation(request);
```

## Quickstart(Async)

### Create Connection

```java
OpenTSDBConfig config = OpenTSDBConfig.builder()
    .host("127.0.0.1")
    .port(4242)
    .socketTimeout(10)
    .connectTimeout(10)
    .connectionRequestTimeout(10)
    .maxConnections(100)
    .maxPerRoute(100)
    .readOnly(false) // if set to true, all methods to write data will throw ReadOnlyException
    .build();

AsyncOpenTSDBClient client = new AsyncOpenTSDBClient(config);

// ... using client

client.close(); // gracefully close after all requests respond
```

### Create Metrics / Tag Keys / Tag Values

```java
// user defined callback object
UIDAssignCallback callback = new UIDAssignCallback() {
  @Override
  public void response(UIDAssignResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};

// creating request is the same as sync version 

// metrics to be created
List<String> metrics = new LinkedList<>();
metrics.add("cpu.0.idle");
metrics.add("cpu.1.idle");
// tag keys to be created
List<String> tagks = new LinkedList<>();
tagks.add("dc");
tagks.add("host");
// tag values to be created
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

// no return, processing response by callback
client.uidAssign(request, callback);
```


### Put Data

```java
// user defined callback object
PutCallback callback = new PutCallback() {
  @Override
  public void response(PutResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};

// creating request is the same as sync version 

// tags for data points
Map<String, String> tags = new LinkedHashMap<>();
tags.put("dc", "sh");
tags.put("host", "host001");
   
// timestamp for data points
// accept second or millisecond 
Long currentTime = System.currentTimeMillis();
   
// data points
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
   .summary(true) // show success and failed number in response
   .details(true) // show detailed erros in response
   .dataPoints(dataPoints)
   .build();

// no return, processing response by callback
client.put(request, callback);
```

### Query Data

```java
// user defined callback object
QueryCallback callback = new QueryCallback() {
  @Override
  public void response(QueryResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};

// creating request is the same as sync version 

// query filters
List<Filter> filters = new LinkedList<>();
// WHERE dc IN ('bj', 'sh') 
filters.add(
    Filter.builder()
        .type(FilterType.LITERAL_OR)
        .tagk("dc")
        .filter("bj|sh")
        .build()
);
// AND host REGEXP 'host.*'
filters.add(
    Filter.builder()
        .type(FilterType.REGEXP)
        .tagk("host")
        .filter("host.*")
        .build()
);
    
// queries
List<Query> queries = new LinkedList<>();
queries.add(
    Query.builder()
        .aggregator(Aggregator.MIN)
        .metric("cpu.0.idle")
        /**
         * While OpenTSDB can store data with millisecond resolution 
         * by default, queries will return the data with second resolution
         * to provide backwards compatibility for existing tools.
         * If you are storing multiple data points per second,
         * make sure that any query you issue includes a 1s-<func> downsampler
         * to read the right data. 
         * Otherwise an indeterminate value will be emitted.
         */
        .downsample("1s-min") // minimum value in 1 second
        .filters(filters)
        .build()
);
queries.add(
    Query.builder()
        .aggregator(Aggregator.AVG)
        .metric("cpu.0.idle")
        .downsample("1s-avg") // average value in 1 second
        .filters(filters)
        .build()
);

QueryRequest request = QueryRequest.builder()
    .start("1d-ago") // start from 1 day ago
    .end("1h-ago") // end at 1 hour ago
    .queries(queries)
    .build();

// no return, processing response by callback
client.query(request2, callback);
```

### Query Last Data Point
```java
QueryLastCallback callback = new QueryLastCallback() {
  @Override
  public void response(QueryLastResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};
Map<String, String> tags = new LinkedHashMap<>();
tags.put("dc", "sh");
tags.put("host", "host001");

List<String> tsuids = new LinkedList<>();
tsuids.add(
    "000001000001000001");

List<LastDataPointQuery> queries = new LinkedList<>();
// query by metric and tags
queries.add(
    LastDataPointQuery.builder()
        .metric("cpu.0.idle")
        .tags(tags)
        .build()
);
// query by tsuid
queries.add(
    LastDataPointQuery.builder()
        .tsuids(tsuids)
        .build()
);

QueryLastRequest request = QueryLastRequest.builder()
    .queries(queries)
    .resolveNames(true)
    .backScan(0)
    .build();

client.queryLast(request, callback);
```

### Delete Data

```java
// user defined callback object
DeleteCallback callback = new DeleteCallback() {
  @Override
  public void response(DeleteResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};

// creating request is the same as sync version 

// query filters
List<Filter> filters = new LinkedList<>();
// WHERE dc IN ('bj', 'sh') 
filters.add(
    Filter.builder()
        .type(FilterType.LITERAL_OR)
        .tagk("dc")
        .filter("bj|sh")
        .build()
);

// queries
List<Query> queries = new LinkedList<>();
queries.add(
    Query.builder()
        .aggregator(Aggregator.NONE) // required parameter by opentsdb, set to anything
        .metric("cpu.1.idle")
        .filters(filters)
        .build()
);

// delete by queries
DeleteRequest request = DeleteRequest.builder()
    .start("2y-ago") // start from 2 years ago
    .end("1y-ago") // end at 1 year ago
    .queries(queries)
    .build();

// no return, processing response by callback
client.delete(request, callback);
```

### Suggest

```java
// search metrics/tag key/tag value by `q` (keyword)
// user defined callback object
SuggestCallback callback = new SuggestCallback() {
  @Override
  public void response(SuggestResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};

// creating request is the same as sync version 

SuggestRequest request = SuggestRequest.builder()
    .type(SuggestType.METRICS)
    .q("cpu")
    .max(100)
    .build();

// no return, processing response by callback
client.suggest(request, callback);
```

### Annotation

#### Create Annotation

```java
CreateAnnotationCallback callback = new CreateAnnotationCallback() {
  @Override
  public void response(CreateAnnotationResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};

Map<String, String> custom = new LinkedHashMap<>();
custom.put("priority", "T0");
custom.put("operator", "user1");

CreateAnnotationRequest request = CreateAnnotationRequest.builder()
    .startTime(1369141261)
    .endTime(1369141262) // null if not completed
    .tsuid("000001000001000001") // null for global event
    .description("Network Outage")
    .notes("Switch #5 died and was replaced")
    .custom(custom)
    .build();

client.createAnnotation(request, callback);
```

#### Get Annotation

```java
GetAnnotationCallback callback = new GetAnnotationCallback() {
  @Override
  public void response(GetAnnotationResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};

GetAnnotationRequest request = GetAnnotationRequest.builder()
    .startTime(1369141261)
    .endTime(1369141262) // not neccesarily
    .tsuid("000001000001000001")
    .build();

client.getAnnotation(request, callback);
```

#### Update Annotation

```java
UpdateAnnotationCallback callback = new UpdateAnnotationCallback() {
  @Override
  public void response(UpdateAnnotationResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};

Map<String, String> custom = new LinkedHashMap<>();
custom.put("priority", "T1");
custom.put("operator", "user2");

UpdateAnnotationRequest request = UpdateAnnotationRequest.builder()
    .startTime(1369141261)
    .endTime(1369141264)
    .tsuid("000001000001000001")
    .description("Network Outage")
    .notes("Switch #5 and #6 died and were replaced")
    .custom(custom)
    .build();

client.updateAnnotation(request, callback);
```

#### Delete Annotation

```java
DeleteAnnotationCallback callback = new DeleteAnnotationCallback() {
  @Override
  public void response(DeleteAnnotationResponse response) {
    // ... processing response
    // System.out.println(response);
  }

  @Override
  public void responseError(ErrorException ee) {
    // .. processing error response
    // System.out.println(ee);
  }

  @Override
  public void failed(Exception e) {
    // .. processing failed request
    // System.out.println(e);
  }
};

DeleteAnnotationRequest request = DeleteAnnotationRequest.builder()
    .startTime(1369141261)
    .endTime(1369141262)
    .tsuid("000001000001000001")
    .build();

// nothing in response when succeeded
client.deleteAnnotation(request, callback);
```

## Authors

- [chocoboxxf]( https://github.com/chocoboxxf)

## License

[Apache License 2.0](LICENSE)