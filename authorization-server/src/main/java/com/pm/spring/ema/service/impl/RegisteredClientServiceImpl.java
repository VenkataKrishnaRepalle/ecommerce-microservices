//package com.pm.spring.ema.service.impl;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
//import org.springframework.security.oauth2.core.oidc.OidcScopes;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
//import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.UUID;
//
//@RequiredArgsConstructor
//@Service
//public class RegisteredClientServiceImpl implements RegisteredClientRepository {
//
//    private final RegisteredClientRepository registeredClientRepository;
//
//    @Override
//    public RegisteredClient findById(String id) {
//        return registeredClientRepository.findById(id);
//    }
//
//    @Override
//    public RegisteredClient findByClientId(String clientId) {
//        return registeredClientRepository.findByClientId(clientId);
//
//    }
//
//    @Override
//    public void save(RegisteredClient registeredClient) {
//
//       registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
//                .clientId("client-id")
//                .clientSecret("client-secret")
//                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
//                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
//                .redirectUri("http://localhost:8080/login/oauth2/code/")
//                .scope(OidcScopes.OPENID)
//                .build();
//
//        registeredClientRepository.save(registeredClient);
//
//    }
//}
