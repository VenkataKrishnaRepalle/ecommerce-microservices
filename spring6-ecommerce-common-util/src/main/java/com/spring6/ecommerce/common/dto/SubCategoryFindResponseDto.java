package com.spring6.ecommerce.common.dto;

import com.spring6.ecommerce.common.enumeration.SubCategoryEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SubCategoryFindResponseDto {
    private UUID id;

    private String name;

    private String alias;

    private String image;

    private SubCategoryEnum status;

    private CategoryFindResponseDto category;
}
