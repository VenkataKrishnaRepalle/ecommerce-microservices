package com.pm.spring.ema.common.util.dto;

import com.pm.spring.ema.common.util.enums.CategoryEnum;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryDto {

  private UUID id;

  private String name;

  private String alias;

  private String imageName;

  private CategoryEnum status;
}
