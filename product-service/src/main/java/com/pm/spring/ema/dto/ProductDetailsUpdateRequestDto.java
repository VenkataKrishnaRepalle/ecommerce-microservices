package com.pm.spring.ema.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDetailsUpdateRequestDto {

    @NotNull
    private String name;

    @NotNull
    private String value;

    @NotNull
    private UUID productId;
}
