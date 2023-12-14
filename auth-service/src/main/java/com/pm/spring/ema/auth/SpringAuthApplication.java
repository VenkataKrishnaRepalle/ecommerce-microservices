package com.pm.spring.ema.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@EnableFeignClients("com.pm.spring.ema.auth")
@EnableWebSecurity(debug=true)
@SpringBootApplication
public class SpringAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.pm.spring.ema.auth.SpringAuthApplication.class, args);
    }

}
