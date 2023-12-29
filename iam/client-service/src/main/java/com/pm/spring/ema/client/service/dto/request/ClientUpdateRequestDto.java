package com.pm.spring.ema.client.service.dto.request;

import com.pm.spring.ema.client.service.model.enums.AuthorizationGrantTypeEnum;
import com.pm.spring.ema.client.service.model.enums.ClientAuthenticationMethodEnum;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Data
@Builder
public class ClientUpdateRequestDto {
    @NotBlank(message = ErrorCodes.E7009)
    private String clientName;

    @NotBlank(message = ErrorCodes.E7010)
    private String clientSecret;

    @NotEmpty(message = ErrorCodes.E7011)
    private Set<ClientAuthenticationMethodEnum> clientAuthenticationMethods;

    @NotEmpty(message = ErrorCodes.E7012)
    private Set<AuthorizationGrantTypeEnum> authorizationGrantTypes;

    @NotEmpty(message = ErrorCodes.E7013)
    private List<String> scopes;

    @NotEmpty(message = ErrorCodes.E7014)
    private List<String> redirectUris;

    @NotEmpty(message = ErrorCodes.E7015)
    private Set<String> postLogoutRedirectUris;

    @NotNull(message = ErrorCodes.E7016)
    private Instant clientSecretExpiresAt;

}
