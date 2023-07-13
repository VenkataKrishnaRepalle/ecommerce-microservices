//package com.spring6.auth.config;
//
//@Configuration
//@EnableAuthorizationServer
//public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
//
//    @Override
//    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//        clients.inMemory()
//                .withClient("your-client-id")
//                .secret("your-client-secret")
//                .authorizedGrantTypes("authorization_code", "refresh_token")
//                .scopes("read", "write")
//                .redirectUris("http://localhost:8080/callback");
//    }
//}