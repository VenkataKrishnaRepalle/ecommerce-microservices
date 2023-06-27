package com.spring6.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableFeignClients("com.spring6.ecommerce")
public class Spring6UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6UserApplication.class, args);
    }

}
