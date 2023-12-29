package com.pm.spring.ema.client.service.dto.response;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Data
@Builder
public class ClientFindResponseDto {
    private String clientId;
    private String clientSecret;
    private String grantTypes;
    private String scopes;
    private String redirectUris;
    private Instant createdOn;
    private Instant lastUpdatedOn;

}
