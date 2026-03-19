package com.pm.spring.ema.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "elastic-query-service")
public class ElasticQueryServiceConfigData {
  private String version;
  private String customAudience;
  private Long backPressureDelayMs;
  private WebClient webClient;
  private Query queryFromKafkaStateStore;
  private Query queryFromAnalyticsDatabase;

  public static class WebClient {
    private Integer connectTimeoutMs;
    private Integer readTimeoutMs;
    private Integer writeTimeoutMs;
    private Integer maxInMemorySize;
    private String contentType;
    private String acceptType;
    private String queryType;

    public Integer getConnectTimeoutMs() {
      return connectTimeoutMs;
    }

    public void setConnectTimeoutMs(Integer connectTimeoutMs) {
      this.connectTimeoutMs = connectTimeoutMs;
    }

    public Integer getReadTimeoutMs() {
      return readTimeoutMs;
    }

    public void setReadTimeoutMs(Integer readTimeoutMs) {
      this.readTimeoutMs = readTimeoutMs;
    }

    public Integer getWriteTimeoutMs() {
      return writeTimeoutMs;
    }

    public void setWriteTimeoutMs(Integer writeTimeoutMs) {
      this.writeTimeoutMs = writeTimeoutMs;
    }

    public Integer getMaxInMemorySize() {
      return maxInMemorySize;
    }

    public void setMaxInMemorySize(Integer maxInMemorySize) {
      this.maxInMemorySize = maxInMemorySize;
    }

    public String getContentType() {
      return contentType;
    }

    public void setContentType(String contentType) {
      this.contentType = contentType;
    }

    public String getAcceptType() {
      return acceptType;
    }

    public void setAcceptType(String acceptType) {
      this.acceptType = acceptType;
    }

    public String getQueryType() {
      return queryType;
    }

    public void setQueryType(String queryType) {
      this.queryType = queryType;
    }
  }

  public static class Query {
    private String method;
    private String accept;
    private String uri;

    public String getMethod() {
      return method;
    }

    public void setMethod(String method) {
      this.method = method;
    }

    public String getAccept() {
      return accept;
    }

    public void setAccept(String accept) {
      this.accept = accept;
    }

    public String getUri() {
      return uri;
    }

    public void setUri(String uri) {
      this.uri = uri;
    }
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public String getCustomAudience() {
    return customAudience;
  }

  public void setCustomAudience(String customAudience) {
    this.customAudience = customAudience;
  }

  public Long getBackPressureDelayMs() {
    return backPressureDelayMs;
  }

  public void setBackPressureDelayMs(Long backPressureDelayMs) {
    this.backPressureDelayMs = backPressureDelayMs;
  }

  public WebClient getWebClient() {
    return webClient;
  }

  public void setWebClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Query getQueryFromKafkaStateStore() {
    return queryFromKafkaStateStore;
  }

  public void setQueryFromKafkaStateStore(Query queryFromKafkaStateStore) {
    this.queryFromKafkaStateStore = queryFromKafkaStateStore;
  }

  public Query getQueryFromAnalyticsDatabase() {
    return queryFromAnalyticsDatabase;
  }

  public void setQueryFromAnalyticsDatabase(Query queryFromAnalyticsDatabase) {
    this.queryFromAnalyticsDatabase = queryFromAnalyticsDatabase;
  }
}
