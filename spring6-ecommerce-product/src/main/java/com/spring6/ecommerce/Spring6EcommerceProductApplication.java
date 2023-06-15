package com.spring6.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableFeignClients("com.spring6.ecommerce")
public class Spring6EcommerceProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring6EcommerceProductApplication.class, args);
	}
}
