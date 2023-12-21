package com.pm.spring.ema.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientCreateRequestDto {
    private String clientId;
    private String clientSecret;
    private String grantTypes;
    private String scopes;
    private String redirectUris;

}
