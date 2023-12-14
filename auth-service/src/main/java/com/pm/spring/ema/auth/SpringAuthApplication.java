package com.pm.spring.ema.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@EnableFeignClients("com.pm.spring.ema.auth")
@EnableWebSecurity(debug=true)
@ComponentScan(basePackages = "com.pm.spring.ema")
@SpringBootApplication
public class SpringAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(com.pm.spring.ema.auth.SpringAuthApplication.class, args);
    }

}
