package com.pm.spring.ema.brand;

//import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

//@EnableAdminServer
@EnableFeignClients("com.pm.spring.ema.brand")
@SpringBootApplication
public class BrandApplication {

    public static void main(String[] args) {

        SpringApplication.run(BrandApplication.class, args);
    }

}
