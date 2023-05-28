package com.spring6.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class CategoryUpdateResponseDto {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String alias;
    @NotBlank
    private String image;
    @NotBlank
    private Boolean isEnabled;

//    private UUID parentCategoryId;
}
