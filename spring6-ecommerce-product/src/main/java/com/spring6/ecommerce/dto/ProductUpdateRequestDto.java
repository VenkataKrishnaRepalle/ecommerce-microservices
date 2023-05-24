package com.spring6.ecommerce.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class ProductUpdateRequestDto {

    private UUID id;

    @NotBlank
    @Size(min = 2, max = 256)
    private String name;

    @NotBlank
    @Size(min = 2, max = 256)
    private String alias;

    @NotNull
    @Size(min = 2, max = 512)
    private String shortDescription;

    @NotNull
    @Size(min = 2, max = 4096)
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
    private Float height;

    @NotNull
    private Float weight;

    private UUID categoryId;

    private UUID brandId;

    @NotNull
    private Boolean enabled;

    @Column(updatable = false)
    private Date createdTime;

    private Date updatedTime;
}
