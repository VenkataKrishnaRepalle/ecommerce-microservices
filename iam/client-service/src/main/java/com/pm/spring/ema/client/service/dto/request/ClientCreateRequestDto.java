package com.pm.spring.ema.client.service.dto.request;

import com.pm.spring.ema.client.service.model.enums.AuthorizationGrantTypeEnum;
import com.pm.spring.ema.client.service.model.enums.ClientAuthenticationMethodEnum;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ClientCreateRequestDto {
    private String name;
    private String clientSecret;
    private List<String> scopes;
    private Set<ClientAuthenticationMethodEnum> clientAuthenticationMethods;
    private Set<AuthorizationGrantTypeEnum> authorizationGrantTypes;
    private List<String> redirectUris;
    private Set<String> postLogoutRedirectUris;
    private Instant clientSecretExpiresAt;

}
