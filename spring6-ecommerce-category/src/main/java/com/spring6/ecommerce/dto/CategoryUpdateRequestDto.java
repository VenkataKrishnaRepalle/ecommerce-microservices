package com.spring6.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class CategoryUpdateRequestDto {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String alias;
    @NotBlank
    private String image;
    @NotNull
    private Boolean isEnabled;

//    private UUID parentCategoryId;
}
