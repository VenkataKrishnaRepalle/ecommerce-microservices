package com.pm.spring.ema.common.util.config;

import java.util.concurrent.Executor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

  @Value("${app.async.core-pool-size:4}")
  private int corePoolSize;

  @Value("${app.async.max-pool-size:8}")
  private int maxPoolSize;

  @Value("${app.async.queue-capacity:32}")
  private int queueCapacity;

  @Value("${app.async.thread-name-prefix:async-}")
  private String threadNamePrefix;

  @Bean(name = "asyncExecutor")
  public Executor asyncExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setCorePoolSize(corePoolSize);
    executor.setMaxPoolSize(maxPoolSize);
    executor.setQueueCapacity(queueCapacity);
    executor.setThreadNamePrefix(threadNamePrefix);
    executor.initialize();
    return executor;
  }
}
