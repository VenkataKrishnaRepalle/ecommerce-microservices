package com.pm.spring.ema.permission.service.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientUpdateRequestDto {
    private String clientId;
    private String clientSecret;
    private String grantTypes;
    private String scopes;
    private String redirectUris;

}
