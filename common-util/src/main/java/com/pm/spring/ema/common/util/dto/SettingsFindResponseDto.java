package com.pm.spring.ema.common.dto;

import com.pm.spring.ema.common.enums.SettingCategory;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class SettingsFindResponseDto {
    private UUID id;

    private String keyName;

    private String valueField;

    private SettingCategory category;

}
