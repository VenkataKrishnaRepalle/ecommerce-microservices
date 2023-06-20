package com.spring6.ecommerce.dto;

import com.spring6.ecommerce.common.enumeration.CategoryEnum;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryUpdateRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String alias;
    @NotBlank
    private String image;
    @NotNull
    private CategoryEnum status;

}
