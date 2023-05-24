package com.spring6.ecommerce.commondto;
import lombok.Builder;
import lombok.Data;
import java.util.UUID;

@Builder
@Data
public class ParentCategoryDto {

    private UUID id;

    private String name;

    private String alias;

    private String image;

    private Boolean isEnabled;

}
