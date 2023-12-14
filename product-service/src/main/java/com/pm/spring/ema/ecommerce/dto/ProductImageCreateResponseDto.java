package com.pm.spring.ema.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;


@Data
@Builder
public class ProductImageCreateResponseDto {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private UUID productId;

}
