package com.spring6.ecommerce.dto;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Data
@Builder
public class ParentCategoryDto {

    private UUID id;

    private String name;

    private String alias;

    private String image;

    private Boolean is_enabled;
}
