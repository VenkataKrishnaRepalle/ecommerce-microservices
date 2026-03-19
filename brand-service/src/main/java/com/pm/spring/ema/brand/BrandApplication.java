package com.pm.spring.ema.brand;

// import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

// @EnableAdminServer
@EnableFeignClients("com.pm.spring.ema.brand")
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.pm.spring.ema", "com.pm.spring.ema.securitycommon"})
@SpringBootApplication
public class BrandApplication {

  public static void main(String[] args) {

    SpringApplication.run(BrandApplication.class, args);
  }
}
