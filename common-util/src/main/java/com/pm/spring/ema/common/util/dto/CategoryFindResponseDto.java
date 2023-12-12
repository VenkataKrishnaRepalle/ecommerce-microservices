package com.pm.spring.ema.common.dto;

import com.pm.spring.ema.common.enums.CategoryEnum;
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
