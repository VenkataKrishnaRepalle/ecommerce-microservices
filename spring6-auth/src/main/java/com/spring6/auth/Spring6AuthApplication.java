package com.spring6.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@EnableFeignClients("com.spring6.auth")
@EnableWebSecurity(debug=true)
@SpringBootApplication
public class Spring6AuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6AuthApplication.class, args);
    }

}
