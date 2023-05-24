package com.spring6.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandCreateRequestDto {
    private UUID id;
    @NotBlank
    @Size(min = 2, max = 45)
    private String name;

    @NotBlank
    private String logo;

    private UUID categoryId;
}
