package com.spring6.ecommerce.dto;

import com.spring6.common.enums.CategoryEnum;
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
    private String image;
    @NotNull
    private CategoryEnum status;

}
