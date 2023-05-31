package com.spring6.ecommerce.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandFindResponesDto {
    private UUID id;
    private String name;

    private String logo;

    private UUID categoryId;
}
