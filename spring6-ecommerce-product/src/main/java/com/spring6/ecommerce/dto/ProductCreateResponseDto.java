package com.spring6.ecommerce.dto;

import com.spring6.ecommerce.entity.ProductImage;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
public class ProductCreateResponseDto {

    private UUID id;

    @NotBlank
    @Size(min = 2, max = 50)
    private String name;

    @NotBlank
    @Size(min = 2, max = 50)
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
    private Float height;

    @NotNull
    private Float weight;

    @Column(nullable = false)
    private String mainImage;

    @NotNull
    private UUID categoryId;

    @NotNull
    private UUID brandId;

    @NotNull
    private Set<ProductImage> images;

    @NotNull
    private Boolean enabled;

    @Column(updatable = false)
    private Date createdTime;

    private Date updatedTime;

}
