package com.pm.spring.ema.client.service.bootstrap;

import com.pm.spring.ema.client.service.model.entity.Client;
import com.pm.spring.ema.client.service.model.enums.AuthorizationGrantTypeEnum;
import com.pm.spring.ema.client.service.model.enums.ClientAuthenticationMethodEnum;
import com.pm.spring.ema.client.service.model.enums.OidcScopesEnum;
import com.pm.spring.ema.client.service.model.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {

    private final ClientRepository clientRepository;

    @Override
    public void run(String... args) throws Exception {
        loadClientData();
    }

    private void loadClientData() {
        if (clientRepository.count() == 0) {
            Client client = Client.builder()
                    .id(UUID.fromString("243a7f2a-584c-40c8-81d0-cbbce0dc1737"))
                    .name("ema")
                    .secret("{noop}secret")
                    .clientAuthenticationMethods(Set.of(ClientAuthenticationMethodEnum.CLIENT_SECRET_BASIC, ClientAuthenticationMethodEnum.CLIENT_SECRET_POST))
                    .authorizationGrantTypes(Set.of(AuthorizationGrantTypeEnum.AUTHORIZATION_CODE, AuthorizationGrantTypeEnum.REFRESH_TOKEN))
                    .redirectUris(List.of("http://127.0.0.1:8080/login/oauth2/code/oidc-client"))
                    .postLogoutRedirectUris(Set.of("http://127.0.0.1:8080/"))
                    .scopes(List.of(OidcScopesEnum.OPENID.getValue(), OidcScopesEnum.PROFILE.getValue(), "products.read"))
                    .clientSecretExpiresAt(Instant.now())
                    .build();

            clientRepository.save(client);

        }
    }
}
