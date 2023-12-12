package com.pm.spring.ema.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class ProductDetailsFindResponseDto {

    private UUID id;

    private String name;

    private String value;

    private UUID productId;
}
