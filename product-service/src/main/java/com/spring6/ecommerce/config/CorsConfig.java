package com.pm.spring.ema.ecommerce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000") // Set the allowed origins for CORS requests
                .allowedMethods("GET", "POST", "PUT", "DELETE") // Set the allowed HTTP methods
                .allowedHeaders("*") // Set the allowed headers
                .allowCredentials(true); // Enable CORS credentials
    }
}
