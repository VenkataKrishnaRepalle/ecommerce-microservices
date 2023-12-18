package com.pm.spring.ema.category.common.dto.subcategoryDto.response;

import com.pm.spring.ema.category.common.enums.SubCategoryEnum;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SubCategoryFindResponseDto {
    private UUID id;

    private String name;

    private String alias;

    private String imageName;

    private SubCategoryEnum status;

}
