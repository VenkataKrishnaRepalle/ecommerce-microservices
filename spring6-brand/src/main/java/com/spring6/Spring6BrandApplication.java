package com.spring6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.text.MessageFormat;

@SpringBootApplication
@EnableFeignClients("com.spring6")
public class Spring6BrandApplication {

    public static void main(String[] args) {
        String message = MessageFormat.format("My name is {0} anI am {1} years old.", "name", 12);
        System.out.println(message);

        SpringApplication.run(Spring6BrandApplication.class, args);
    }

}
