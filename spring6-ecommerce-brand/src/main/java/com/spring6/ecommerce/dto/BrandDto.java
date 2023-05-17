package com.spring6.ecommerce.dto;

import lombok.Data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
public class BrandDto {
    private UUID id;
    private String name;
    private String logo;
    private Set<CategoryDto> categories = new HashSet<>();
}
