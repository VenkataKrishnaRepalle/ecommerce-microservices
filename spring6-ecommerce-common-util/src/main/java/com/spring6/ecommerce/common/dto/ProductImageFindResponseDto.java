package com.spring6.ecommerce.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductImageFindResponseDto {

    private UUID id;

    private String name;

    private UUID productId;

}
