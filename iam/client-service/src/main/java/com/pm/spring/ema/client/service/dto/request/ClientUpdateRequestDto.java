package com.pm.spring.ema.client.service.dto.request;

import com.pm.spring.ema.client.service.model.enums.AuthorizationGrantTypeEnum;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@Builder
public class ClientUpdateRequestDto {
    private String clientId;
    private String clientSecret;
    private Set<AuthorizationGrantTypeEnum> authorizationGrantTypes;
    private List<String> scopes;
    private List<String> redirectUris;

}
