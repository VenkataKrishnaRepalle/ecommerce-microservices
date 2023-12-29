package com.pm.spring.ema.client.service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ClientCreateResponseDto {
    private String name;
    private String clientId;
    private String clientSecret;
    private String grantTypes;
    private String scopes;
    private String redirectUris;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
