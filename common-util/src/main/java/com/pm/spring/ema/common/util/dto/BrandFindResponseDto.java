package com.pm.spring.ema.common.util.dto;

import com.pm.spring.ema.common.util.enums.BrandStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class BrandFindResponseDto {
    private UUID id;
    private UUID subcategoryId;
    private String name;
    private BrandStatusEnum status;
    private String createdOn;
    private String lastUpdatedOn;
}
