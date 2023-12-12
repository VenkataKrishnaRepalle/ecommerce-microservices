package com.pm.spring.ema.settings.dto;

import com.pm.spring.ema.common.enums.SettingCategory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingsCreateResponseDto {

    @NotBlank
    private String keyName;

    @NotBlank
    private String value;

    @NotNull
    private SettingCategory category;

}
