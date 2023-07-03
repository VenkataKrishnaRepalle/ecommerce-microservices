package com.spring6.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableFeignClients("com.spring6.user")
public class Spring6AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6AuthApplication.class, args);
    }

}
