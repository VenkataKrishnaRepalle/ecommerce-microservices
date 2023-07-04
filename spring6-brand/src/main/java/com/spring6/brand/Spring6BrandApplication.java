package com.spring6.brand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.spring6.brand")
public class Spring6BrandApplication {

    public static void main(String[] args) {

        SpringApplication.run(Spring6BrandApplication.class, args);
    }

}
