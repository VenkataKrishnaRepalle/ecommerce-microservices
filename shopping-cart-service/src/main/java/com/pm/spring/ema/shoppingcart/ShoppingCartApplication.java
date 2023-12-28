package com.pm.spring.ema.shoppingcart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.pm.spring.ema")
@SpringBootApplication
public class ShoppingCartApplication {

    public static void main(String[] args) {

        SpringApplication.run(ShoppingCartApplication.class, args);
    }

}
