package com.pm.spring.ema.category;

import com.pm.spring.ema.common.util.exception.GlobalExceptionHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan(basePackages = {"com.pm.spring.ema.category", "com.pm.spring.ema.securitycommon"})
@Import(GlobalExceptionHandler.class)
public class CategoryApplication {

  public static void main(String[] args) {
    SpringApplication.run(CategoryApplication.class, args);
  }
}
