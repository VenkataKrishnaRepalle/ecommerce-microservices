package com.pm.spring.ema.category.common.dto.categoryDto.response;

import com.pm.spring.ema.category.common.enums.CategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class CategoryCreateResponseDto {

    @NotNull
    private UUID id;

    @NotBlank
    private String name;

    @NotBlank
    private String alias;

    @NotBlank
    private String imageName;

    @NotNull
    private CategoryEnum status;

}
