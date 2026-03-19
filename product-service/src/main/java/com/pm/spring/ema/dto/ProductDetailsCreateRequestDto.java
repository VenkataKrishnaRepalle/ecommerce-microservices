package com.pm.spring.ema.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductDetailsCreateRequestDto {

  @NotNull private String name;

  @NotNull private String value;

  @NotNull private UUID productId;
}
