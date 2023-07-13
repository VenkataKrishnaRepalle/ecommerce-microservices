package com.spring6.common.dto;

import com.spring6.common.enums.BrandStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class BrandFindResponseDto {
    private UUID id;
    private String name;
    private BrandStatusEnum status;
    private String createdOn;
    private String lastUpdatedOn;
    private UUID subcategoryId;
}
