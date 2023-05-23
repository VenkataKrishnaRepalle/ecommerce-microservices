package com.spring6.ecommerce.dto;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Builder
@Data
public class CategoryDto {

    private UUID id;

    private String name;

    private String alias;

    private String image;

    private Boolean isEnabled;

}
