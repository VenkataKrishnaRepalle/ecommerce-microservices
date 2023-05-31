package com.spring6.ecommerce.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryFindResponseDto {
    private UUID id;

    private String name;

    private String alias;

    private String image;

    private Boolean isEnabled;

    private ParentCategoryFindResponseDto parentCategory;
}
