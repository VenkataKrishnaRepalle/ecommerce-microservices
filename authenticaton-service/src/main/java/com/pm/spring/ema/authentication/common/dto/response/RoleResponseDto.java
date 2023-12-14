package com.pm.spring.ema.authentication.common.dto.response;

import com.pm.spring.ema.authentication.common.model.enums.RoleType;
import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.UUID;

@Builder
@Data
public class RoleResponseDto {

     private UUID id;
     private RoleType name;
    private String description;
    private Instant createdOn;
    private Instant lastUpdatedOn;
}
