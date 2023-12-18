package com.pm.spring.ema.category.common.dto.categoryDto.response;

import com.pm.spring.ema.category.common.enums.CategoryEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryFindResponseDto {
    private UUID id;

    private String name;

    private String alias;

    private String imageName;

    private CategoryEnum status;

}
