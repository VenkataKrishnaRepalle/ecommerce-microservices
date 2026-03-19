package com.pm.spring.ema.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka-streams-service")
public class KafkaStreamsServiceConfigData {
  private String version;
  private String customAudience;

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
