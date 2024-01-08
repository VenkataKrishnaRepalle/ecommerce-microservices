package com.pm.spring.ema.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class ProductCreateResponseDto {

    @NotNull
    private UUID id;

    @NotNull
    private String name;

    @NotNull
    private String alias;

    @NotNull
    private String shortDescription;

    @NotNull
    private String fullDescription;

    @NotNull
    private Boolean inStock;

    @NotNull
    private BigDecimal cost;

    @NotNull
    private BigDecimal price;

    @NotNull
    private BigDecimal discountPercent;

    @NotNull
    private Float length;

    @NotNull
    private Float width;

    @NotNull
    private Float weight;

    @NotNull
    private Float height;

    @NotNull
    private UUID categoryId;

    @NotNull
    private UUID brandId;

    @NotNull
    private Boolean isEnabled;
}
