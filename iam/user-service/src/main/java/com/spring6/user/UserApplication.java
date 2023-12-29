package com.pm.spring.ema.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableFeignClients("com.pm.spring.ema.ecommerce")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.pm.spring.ema.user.UserApplication.class, args);
    }

}
