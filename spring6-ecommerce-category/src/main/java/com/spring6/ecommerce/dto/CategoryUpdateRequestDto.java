package com.spring6.ecommerce.dto;

import java.util.UUID;

public class CategoryUpdateRequestDto {
    private UUID id;

    private String name;

    private String alias;

    private String image;

    private Boolean isEnabled;

    private ParentCategoryDto parentCategory;
}
