package com.spring6.ecommerce.dto;

import com.spring6.common.enums.SubCategoryEnum;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class SubCategoryUpdateResponseDto {
    private UUID id;
    @NotBlank
    private String name;
    @NotBlank
    private String alias;
    @NotBlank
    private String image;
    @NotBlank
    private SubCategoryEnum status;

}
