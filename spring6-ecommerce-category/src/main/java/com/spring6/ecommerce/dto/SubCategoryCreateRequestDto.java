package com.spring6.ecommerce.dto;

import com.spring6.ecommerce.common.enumeration.SubCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class SubCategoryCreateRequestDto {
    @NotBlank
    private String name;
    @NotBlank
    private String alias;
    @NotBlank
    private String image;
    @NotNull
    private SubCategoryEnum status;
    @NotNull
    private UUID categoryUUID;
}
