package com.spring6.ecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients("com.spring6.ecommerce")
public class Spring6ProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(Spring6ProductApplication.class, args);
	}
}
