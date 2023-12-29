package com.pm.spring.ema.group.service.service;

import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;

@Service
public interface RegisteredClientService {
    
    public RegisteredClient findById(String id);

    public RegisteredClient findByClientId(String clientId);

    public void save(RegisteredClient registeredClient);
}
