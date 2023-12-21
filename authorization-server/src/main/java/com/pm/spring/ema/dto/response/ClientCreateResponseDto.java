package com.pm.spring.ema.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientCreateResponseDto {
    private String clientId;
    private String clientSecret;
    private String grantTypes;
    private String scopes;
    private String redirectUris;

}
