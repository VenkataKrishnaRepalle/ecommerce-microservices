package com.pm.spring.ema.config.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain webSecurityCustomizer(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(
            requests ->
                requests
                    .requestMatchers(
                        new AntPathRequestMatcher("/actuator/**"),
                        new AntPathRequestMatcher("/encrypt/**"),
                        new AntPathRequestMatcher("/decrypt/**"))
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .csrf(csrf -> csrf.ignoringRequestMatchers("/encrypt/**", "/decrypt/**"))
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }
}
