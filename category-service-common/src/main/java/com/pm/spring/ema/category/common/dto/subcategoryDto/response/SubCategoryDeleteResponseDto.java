package com.pm.spring.ema.category.common.dto.subcategoryDto.response;

import com.pm.spring.ema.category.common.enums.SubCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class SubCategoryDeleteResponseDto {

    @NotNull
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String alias;

    @NotBlank
    private String imageName;

    @NotNull
    private SubCategoryEnum status;
}
