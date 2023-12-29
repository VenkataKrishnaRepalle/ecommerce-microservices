package com.pm.spring.ema.client.service.dto.response;

import com.pm.spring.ema.client.service.model.enums.AuthorizationGrantTypeEnum;
import com.pm.spring.ema.client.service.model.enums.ClientAuthenticationMethodEnum;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ClientUpdateResponseDto {
    private UUID clientId;
    private String name;
    private List<String> scopes;
    private Set<ClientAuthenticationMethodEnum> clientAuthenticationMethods;
    private Set<AuthorizationGrantTypeEnum> authorizationGrantTypes;
    private List<String> redirectUris;
    private Set<String> postLogoutRedirectUris;
    private Instant clientSecretExpiresAt;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
