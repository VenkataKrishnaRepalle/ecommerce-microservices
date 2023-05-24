package com.spring6.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients("com.spring6.ecommerce")
@ImportAutoConfiguration({FeignAutoConfiguration.class})
public class Spring6EcommerceBrandApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6EcommerceBrandApplication.class, args);
    }

}
