package com.spring6.ecommerce.dto;

import com.spring6.common.enums.SubCategoryEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SubCategoryCreateResponseDto {
    private UUID id;
    private String name;
    private String alias;
    private String image;
    private SubCategoryEnum status;
    private UUID categoryUUID;
}
