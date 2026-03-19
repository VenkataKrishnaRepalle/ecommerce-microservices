package com.pm.spring.ema.common.dto;

import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsFindResponseDto {

  private UUID id;

  private String name;

  private String value;

  private UUID productId;
}
