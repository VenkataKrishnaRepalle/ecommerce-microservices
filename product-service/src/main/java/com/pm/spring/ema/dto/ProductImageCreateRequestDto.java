package com.pm.spring.ema.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import lombok.Data;

@Data
public class ProductImageCreateRequestDto {

  @NotNull private String name;

  @NotNull private UUID productId;
}
