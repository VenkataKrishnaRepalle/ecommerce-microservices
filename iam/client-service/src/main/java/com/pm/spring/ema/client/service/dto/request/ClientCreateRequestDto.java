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
public class ClientCreateRequestDto {
    @NotBlank(message = ErrorCodes.E7001)
    private String clientName;

    @NotBlank(message = ErrorCodes.E7002)
    private String clientSecret;

    @NotEmpty(message = ErrorCodes.E7003)
    private List<String> scopes;

    @NotEmpty(message = ErrorCodes.E7004)
    private Set<ClientAuthenticationMethodEnum> clientAuthenticationMethods;

    @NotEmpty(message = ErrorCodes.E7005)
    private Set<AuthorizationGrantTypeEnum> authorizationGrantTypes;

    @NotEmpty(message = ErrorCodes.E7006)
    private List<String> redirectUris;

    @NotEmpty(message = ErrorCodes.E7007)
    private Set<String> postLogoutRedirectUris;

    @NotNull(message = ErrorCodes.E7008)
    private Instant clientSecretExpiresAt;

}
