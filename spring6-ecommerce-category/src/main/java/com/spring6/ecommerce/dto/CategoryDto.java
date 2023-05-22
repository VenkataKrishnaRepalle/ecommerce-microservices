package com.spring6.ecommerce.dto;

import com.spring6.ecommerce.entity.ChildCategory;
import com.spring6.ecommerce.entity.ParentCategory;
import lombok.Builder;
import lombok.Data;


import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Builder
@Data
public class CategoryDto {

    private UUID id;

    private String name;

    private String alias;

    private String image;

    private Boolean is_enabled;

    private ParentCategory parent;

    private Set<ChildCategory> children = new HashSet<>();

}
