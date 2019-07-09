package net.opentsdb.client;

/**
 * OpenTSDB client configuration
 */
public class OpenTSDBConfig {

  /**
   * OpenTSDB TSD Host Address
   */
  private String host;

  /**
   * OpenTSDB TSD Host Port
   */
  private int port;
 
  /**
   * Read Only Mode
   */
  private boolean readOnly = false;

  /**
   * Timeout in seconds for waiting for data or, put differently,
   * a maximum period inactivity between two consecutive data packets
   */
  private int socketTimeout;

  /**
   * Timeout in seconds until a connection is established.
   */
  private int connectTimeout;

  /**
   * Timeout in seconds used when requesting a connection from the connection manager 
   */
  private int connectionRequestTimeout;

  /**
   * Max concurrent connections
   */
  private int maxConnections = 100;

  /**
   * Max concurrent connections for one route
   */
  private int maxPerRoute = 100;

  public OpenTSDBConfig() {
  }

  public String getHost() {
    return host;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public int getPort() {
    return port;
  }

  public void setPort(int port) {
    this.port = port;
  }

  public int getSocketTimeout() {
    return socketTimeout;
  }

  public void setSocketTimeout(int socketTimeout) {
    this.socketTimeout = socketTimeout;
  }

  public int getConnectTimeout() {
    return connectTimeout;
  }

  public void setConnectTimeout(int connectTimeout) {
    this.connectTimeout = connectTimeout;
  }

  public int getConnectionRequestTimeout() {
    return connectionRequestTimeout;
  }

  public void setConnectionRequestTimeout(int connectionRequestTimeout) {
    this.connectionRequestTimeout = connectionRequestTimeout;
  }

  public int getMaxConnections() {
    return maxConnections;
  }

  public void setMaxConnections(int maxConnections) {
    this.maxConnections = maxConnections;
  }

  public int getMaxPerRoute() {
    return maxPerRoute;
  }

  public void setMaxPerRoute(int maxPerRoute) {
    this.maxPerRoute = maxPerRoute;
  }

  public boolean isReadOnly() {
    return readOnly;
  }

  public void setReadOnly(boolean readOnly) {
    this.readOnly = readOnly;
  }

  public static OpenTSDBConfigBuilder builder() {
    return new OpenTSDBConfigBuilder();
  }

  public static final class OpenTSDBConfigBuilder {

    private String host;
    private int port;
    private int socketTimeout;
    private int connectTimeout;
    private int connectionRequestTimeout;
    private int maxConnections = 100;
    private int maxPerRoute = 100;
    private boolean readOnly = false;

    private OpenTSDBConfigBuilder() {
    }

    public OpenTSDBConfigBuilder host(String host) {
      this.host = host;
      return this;
    }

    public OpenTSDBConfigBuilder port(int port) {
      this.port = port;
      return this;
    }

    public OpenTSDBConfigBuilder socketTimeout(int socketTimeout) {
      this.socketTimeout = socketTimeout;
      return this;
    }

    public OpenTSDBConfigBuilder connectTimeout(int connectTimeout) {
      this.connectTimeout = connectTimeout;
      return this;
    }

    public OpenTSDBConfigBuilder connectionRequestTimeout(int connectionRequestTimeout) {
      this.connectionRequestTimeout = connectionRequestTimeout;
      return this;
    }

    public OpenTSDBConfigBuilder maxConnections(int maxConnections) {
      this.maxConnections = maxConnections;
      return this;
    }

    public OpenTSDBConfigBuilder maxPerRoute(int maxPerRoute) {
      this.maxPerRoute = maxPerRoute;
      return this;
    }

    public OpenTSDBConfigBuilder readOnly(boolean readOnly) {
      this.readOnly = readOnly;
      return this;
    }

    public OpenTSDBConfig build() {
      OpenTSDBConfig openTSDBConfig = new OpenTSDBConfig();
      openTSDBConfig.setHost(host);
      openTSDBConfig.setPort(port);
      openTSDBConfig.setSocketTimeout(socketTimeout);
      openTSDBConfig.setConnectTimeout(connectTimeout);
      openTSDBConfig.setConnectionRequestTimeout(connectionRequestTimeout);
      openTSDBConfig.setMaxConnections(maxConnections);
      openTSDBConfig.setMaxPerRoute(maxPerRoute);
      openTSDBConfig.setReadOnly(readOnly);
      return openTSDBConfig;
    }
  }
}
