package com.pm.spring.ema.config;

import com.pm.spring.ema.model.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.builders.ClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerConfigurerAdapter;

@Configuration
@EnableAuthorizationServer
@EnableResourceServer
public class OAuth2AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public void configure(ClientDetailsServiceBuilder clients) throws Exception {
        clients
                .jdbc()
                .dataSource(dataSource)  // Use the same data source as defined in application.properties
                .passwordEncoder(passwordEncoder)
                .withClientDetails(clientDetailsService());
    }

    // Other configurations...

    private ClientDetailsService clientDetailsService() {
        return clientId -> clientRepository.findById(clientId)
                .map(client -> {
                    BaseClientDetails details = new BaseClientDetails(
                            client.getClientId(),
                            client.getResourceIds(),
                            client.getScopes(),
                            client.getAuthorizedGrantTypes(),
                            client.getAuthorities(),
                            client.getRedirectUris());
                    details.setClientSecret(client.getClientSecret());
                    return details;
                })
                .orElse(null);
    }
}
