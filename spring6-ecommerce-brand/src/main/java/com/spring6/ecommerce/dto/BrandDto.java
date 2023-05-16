package com.spring6.ecommerce.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class BrandDto {

    private Integer id;
    private String name;
    private String logo;
    private Set<CategoryDto> categories = new HashSet<>();
}
