package com.pm.spring.ema.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "message-service")
public class MessageServiceConfigData {
  private String version;
  private String customAudience;

  //    private Long backPressureDelayMs;
  //    private WebClient webClient;
  //    private Query queryFromKafkaStateStore;
  //    private Query queryFromAnalyticsDatabase;
  //
  //    @Data
  //    public static class WebClient {
  //        private Integer connectTimeoutMs;
  //        private Integer readTimeoutMs;
  //        private Integer writeTimeoutMs;
  //        private Integer maxInMemorySize;
  //        private String contentType;
  //        private String acceptType;
  //        private String queryType;
  //    }
  //
  //    @Data
  //    public static class Query {
  //        private String method;
  //        private String accept;
  //        private String uri;
  //    }

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
}
