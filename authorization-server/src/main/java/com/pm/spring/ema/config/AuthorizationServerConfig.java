//package com.pm.spring.ema.config;
//
//
////import com.pm.spring.ema.service.impl.RegisteredClientServiceImpl;
////import lombok.RequiredArgsConstructor;
////import org.springframework.context.annotation.Bean;
////import org.springframework.context.annotation.Configuration;
////import org.springframework.context.annotation.Import;
////import org.springframework.core.Ordered;
////import org.springframework.core.annotation.Order;
////import org.springframework.security.config.Customizer;
////import org.springframework.security.config.annotation.web.builders.HttpSecurity;
////import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;
////import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
////import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
////import org.springframework.security.web.SecurityFilterChain;
////
////@Configuration
////@Import(OAuth2AuthorizationServerConfiguration.class)
////@RequiredArgsConstructor
////public class AuthorizationServerConfig {
////
////    private final RegisteredClientServiceImpl jdbcRegisteredClientRepository;
////
////    @Bean
////    public RegisteredClientRepository registeredClientRepository() {
////        return jdbcRegisteredClientRepository;
////    }
////
////    @Bean
////    @Order(Ordered.HIGHEST_PRECEDENCE)
////    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
////        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
////        return http.formLogin(Customizer.withDefaults()).build();
////    }
////}
//
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Import;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.UUID;
//
//@Import(OAuth2AuthorizationServerConfiguration.class)
//@Configuration
//@EnableWebSecurity
//public class AuthorizationServerConfig {
//
//    @Bean
//    public RegisteredClientRepository registeredClientRepository() {
//        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("test-client-id")
//                .clientSecret("{noop}client-secret")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("http://localhost:8080/login/oauth2/code/client-id")
//                .scope("read")
//                .scope("write")
//                .build();
//        return new InMemoryRegisteredClientRepository(registeredClient);
//    }
//
//    @Bean
//    public UserDetailsService users() {
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("password")
//                .roles("USER")
//                .build();
//        return new InMemoryUserDetailsManager(user);
//    }
//
//    @Bean
//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
//        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
//        return http.formLogin(Customizer.withDefaults()).build();
//    }
//}