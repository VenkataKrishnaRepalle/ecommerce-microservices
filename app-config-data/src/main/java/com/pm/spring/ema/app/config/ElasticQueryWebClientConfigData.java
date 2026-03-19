package com.pm.spring.ema.app.config;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "elastic-query-web-client")
public class ElasticQueryWebClientConfigData {
  private WebClient webClient;
  private Query queryByText;

  public static class WebClient {
    private Integer connectTimeoutMs;
    private Integer readTimeoutMs;
    private Integer writeTimeoutMs;
    private Integer maxInMemorySize;
    private String contentType;
    private String acceptType;
    private String baseUrl;
    private String serviceId;
    private List<Instance> instances;

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

    public String getBaseUrl() {
      return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
      this.baseUrl = baseUrl;
    }

    public String getServiceId() {
      return serviceId;
    }

    public void setServiceId(String serviceId) {
      this.serviceId = serviceId;
    }

    public List<Instance> getInstances() {
      return instances;
    }

    public void setInstances(List<Instance> instances) {
      this.instances = instances;
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

  public static class Instance {
    private String id;
    private String host;
    private Integer port;

    public String getId() {
      return id;
    }

    public void setId(String id) {
      this.id = id;
    }

    public String getHost() {
      return host;
    }

    public void setHost(String host) {
      this.host = host;
    }

    public Integer getPort() {
      return port;
    }

    public void setPort(Integer port) {
      this.port = port;
    }
  }

  public WebClient getWebClient() {
    return webClient;
  }

  public void setWebClient(WebClient webClient) {
    this.webClient = webClient;
  }

  public Query getQueryByText() {
    return queryByText;
  }

  public void setQueryByText(Query queryByText) {
    this.queryByText = queryByText;
  }
}
