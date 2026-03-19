package com.pm.spring.ema.common.util.config;

import com.pm.spring.ema.common.util.exception.CustomErrorDecoder;
import feign.RequestInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Configuration
public class CustomFeignConfiguration {

  @Bean
  public CustomErrorDecoder customErrorDecoder() {
    return new CustomErrorDecoder();
  }

  @Bean
  public RequestInterceptor requestInterceptor() {
    return requestTemplate -> {
      ServletRequestAttributes attributes =
          (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (attributes != null) {
        String authorizationHeader = attributes.getRequest().getHeader("Authorization");
        if (authorizationHeader != null && !authorizationHeader.isEmpty()) {
          requestTemplate.header("Authorization", authorizationHeader);
        }
      }
    };
  }
}
