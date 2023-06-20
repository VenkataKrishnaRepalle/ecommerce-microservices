package com.spring6.common.dto;

import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class ProductFindResponseDto {

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

    private Float weight;

    private Float height;

    private String mainImage;

    private UUID categoryId;

    private UUID brandId;

    private Boolean isEnabled;

    private Date createdTime;

    private Date updatedTime;
}
