package com.spring6.common.dto;

import com.spring6.common.enums.SettingCategory;
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
