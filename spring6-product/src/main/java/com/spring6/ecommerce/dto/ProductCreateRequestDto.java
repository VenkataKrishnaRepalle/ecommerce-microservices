package com.spring6.ecommerce.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductCreateRequestDto {
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
    private Float cost;

    @NotNull
    private Float price;

    private Float discountPercent;

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
