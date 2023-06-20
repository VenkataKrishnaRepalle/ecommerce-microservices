package com.spring6.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductDetailsUpdateResponseDto {

    @NotNull
    private String name;

    @NotNull
    private String value;

    @NotNull
    private UUID productId;
}
