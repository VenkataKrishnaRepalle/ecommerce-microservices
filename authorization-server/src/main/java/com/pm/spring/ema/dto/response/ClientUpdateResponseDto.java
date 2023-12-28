package com.pm.spring.ema.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class ClientUpdateResponseDto {
    private String clientId;
    private String clientSecret;
    private String grantTypes;
    private String scopes;
    private String redirectUris;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
