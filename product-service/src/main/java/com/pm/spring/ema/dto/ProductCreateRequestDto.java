package com.pm.spring.ema.dto;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductCreateRequestDto {

    @NotNull
    @NotBlank
    private String name;

    @NotNull
    @NotBlank
    private String alias;

    @NotNull
    @NotBlank
    private String shortDescription;

    @NotNull
    @NotBlank
    private String fullDescription;

    @NotNull
    private Boolean inStock;

    @NotNull
    @PositiveOrZero
    private BigDecimal cost;

    @NotNull
    @PositiveOrZero
    private BigDecimal price;

    private BigDecimal discountPercent;

    @NotNull
    @Positive
    private Float length;

    @NotNull
    @Positive
    private Float width;

    @NotNull
    @Positive
    private Float weight;

    @NotNull
    @Positive
    private Float height;

    @NotNull
    private UUID categoryId;

    @NotNull
    private UUID brandId;

    @NotNull
    @Enumerated
    private Boolean isEnabled;
}