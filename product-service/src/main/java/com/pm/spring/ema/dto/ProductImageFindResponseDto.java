package com.pm.spring.ema.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;


@Data
public class ProductImageFindResponseDto {


    @NotNull
    private String name;

    @NotNull
    private UUID productId;

}
