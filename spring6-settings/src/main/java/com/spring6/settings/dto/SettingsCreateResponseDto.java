package com.spring6.settings.dto;

import com.spring6.common.enums.SettingCategory;
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
