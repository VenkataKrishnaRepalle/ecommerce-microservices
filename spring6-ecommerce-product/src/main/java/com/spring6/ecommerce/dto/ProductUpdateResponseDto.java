package com.spring6.ecommerce.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class ProductUpdateResponseDto {

    private UUID id;

    private String name;

    private String alias;

    private String shortDescription;

    private String fullDescription;

    private Boolean inStock;

    private Float cost;

    private Float price;

    private Float discountPercent;

    private Float length;

    private Float width;

    private Float height;

    private Float weight;

    private UUID categoryId;

    private UUID brandId;

    private Boolean enabled;

    private Date createdTime;

    private Date updatedTime;

}
