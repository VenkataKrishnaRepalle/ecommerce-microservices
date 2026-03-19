package com.pm.spring.ema.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductDetailsCreateResponseDto {

  @NotNull private String name;

  @NotNull private String value;

  @NotNull private UUID productId;
}
