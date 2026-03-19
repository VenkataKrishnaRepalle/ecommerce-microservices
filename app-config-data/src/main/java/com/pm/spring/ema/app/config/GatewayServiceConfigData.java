package com.pm.spring.ema.app.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "gateway-service")
public class GatewayServiceConfigData {
  private Long timeoutMs;
  private Float failureRateThreshold;
  private Float slowCallRateThreshold;
  private Long slowCallDurationThreshold;
  private Integer permittedNumOfCallsInHalfOpenState;
  private Integer slidingWindowSize;
  private Integer minNumberOfCalls;
  private Long waitDurationInOpenState;

  public Long getTimeoutMs() {
    return timeoutMs;
  }

  public void setTimeoutMs(Long timeoutMs) {
    this.timeoutMs = timeoutMs;
  }

  public Float getFailureRateThreshold() {
    return failureRateThreshold;
  }

  public void setFailureRateThreshold(Float failureRateThreshold) {
    this.failureRateThreshold = failureRateThreshold;
  }

  public Float getSlowCallRateThreshold() {
    return slowCallRateThreshold;
  }

  public void setSlowCallRateThreshold(Float slowCallRateThreshold) {
    this.slowCallRateThreshold = slowCallRateThreshold;
  }

  public Long getSlowCallDurationThreshold() {
    return slowCallDurationThreshold;
  }

  public void setSlowCallDurationThreshold(Long slowCallDurationThreshold) {
    this.slowCallDurationThreshold = slowCallDurationThreshold;
  }

  public Integer getPermittedNumOfCallsInHalfOpenState() {
    return permittedNumOfCallsInHalfOpenState;
  }

  public void setPermittedNumOfCallsInHalfOpenState(Integer permittedNumOfCallsInHalfOpenState) {
    this.permittedNumOfCallsInHalfOpenState = permittedNumOfCallsInHalfOpenState;
  }

  public Integer getSlidingWindowSize() {
    return slidingWindowSize;
  }

  public void setSlidingWindowSize(Integer slidingWindowSize) {
    this.slidingWindowSize = slidingWindowSize;
  }

  public Integer getMinNumberOfCalls() {
    return minNumberOfCalls;
  }

  public void setMinNumberOfCalls(Integer minNumberOfCalls) {
    this.minNumberOfCalls = minNumberOfCalls;
  }

  public Long getWaitDurationInOpenState() {
    return waitDurationInOpenState;
  }

  public void setWaitDurationInOpenState(Long waitDurationInOpenState) {
    this.waitDurationInOpenState = waitDurationInOpenState;
  }
}
