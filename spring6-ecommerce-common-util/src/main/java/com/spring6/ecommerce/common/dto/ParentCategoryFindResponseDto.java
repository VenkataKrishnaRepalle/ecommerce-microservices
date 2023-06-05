package com.spring6.ecommerce.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ParentCategoryFindResponseDto {
    private UUID id;

    private String name;

    private String alias;

    private String image;

    private Boolean isEnabled;

    private Set<CategoryFindResponseDto> categories = new HashSet<>();
}
