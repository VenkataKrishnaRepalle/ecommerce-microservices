package com.spring6.ecommerce.commondto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Data
public class BrandDto {
    private UUID id;
    @NotBlank
    @Size(min = 2, max = 45)
    private String name;

    @NotBlank
    private String logo;

    private UUID categoryId;
}
