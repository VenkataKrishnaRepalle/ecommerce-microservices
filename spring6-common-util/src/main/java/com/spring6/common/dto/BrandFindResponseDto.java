package com.spring6.common.dto;

import com.spring6.common.enums.BrandStatusEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandFindResponseDto {
    private UUID id;
    private String name;

    private String logo;

    private BrandStatusEnum status;

    private UUID subcategoryId;
}
