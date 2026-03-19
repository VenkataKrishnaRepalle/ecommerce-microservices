package com.pm.spring.ema.common.dto;

import com.pm.spring.ema.common.enums.SettingCategory;
import java.util.UUID;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingsFindResponseDto {
  private UUID id;

  private String keyName;

  private String valueField;

  private SettingCategory category;
}
