package com.spring6.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class BrandCreateRequestDto {
    @NotBlank
    @Size(min = 2, max = 45)
    private String name;

    @NotNull
    @NotEmpty
    private List<BrandCategoryDto> brandCategories;

    @NotNull
    private Boolean isEnabled;
}
