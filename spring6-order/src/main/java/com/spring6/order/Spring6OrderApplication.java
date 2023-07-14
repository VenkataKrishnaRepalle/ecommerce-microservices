package com.spring6.order;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableAdminServer
@EnableFeignClients("com.spring6.order")
@SpringBootApplication
public class Spring6OrderApplication {

    public static void main(String[] args) {

        SpringApplication.run(Spring6OrderApplication.class, args);
    }

}
