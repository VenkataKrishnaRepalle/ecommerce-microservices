package com.spring6.ecommerce.common.dto;

import com.spring6.ecommerce.common.enumeration.CategoryEnum;
import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class CategoryFindResponseDto {
    private UUID id;

    private String name;

    private String alias;

    private String image;

    private CategoryEnum status;
    private Set<SubCategoryFindResponseDto> categories = new HashSet<>();
}
