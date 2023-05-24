package com.spring6.ecommerce.dto;


import com.spring6.ecommerce.entity.Brand;
import com.spring6.ecommerce.entity.Category;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class ProductDto {

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

    private Category category;

    private Brand brand;

    private Boolean enabled;

    private Date createdTime;

    private Date updatedTime;

}
