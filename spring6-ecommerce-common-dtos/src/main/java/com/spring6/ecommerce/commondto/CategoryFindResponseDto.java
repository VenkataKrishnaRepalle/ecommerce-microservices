package com.spring6.ecommerce.commondto;

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

    private ParentCategoryDto parentCategory;
}
