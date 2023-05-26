package com.spring6.ecommerce.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class ParentCategoryCreateRequestDto {
    private UUID id;

    private String name;

    private String alias;

    private String image;

    private Boolean isEnabled;

//    private Set<CategoryFindResponseDto> categories = new HashSet<>();
}
