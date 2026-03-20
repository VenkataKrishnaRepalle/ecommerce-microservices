package com.pm.spring.ema.common.util.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDto {

  private UUID id;

  private String name;

  private String value;

  private UUID productId;
}
