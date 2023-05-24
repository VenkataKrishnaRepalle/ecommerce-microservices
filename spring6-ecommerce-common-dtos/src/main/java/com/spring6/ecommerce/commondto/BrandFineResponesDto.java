package com.spring6.ecommerce.commondto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class BrandFineResponesDto {
    private UUID id;
    private String name;

    private String logo;

    private UUID categoryId;
}
