package com.pm.spring.ema.authentication.common.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class PermissionResponseDto {

    private UUID id;

    private String name;

    private Instant createdOn;

    private Instant lastUpdatedOn;

}
