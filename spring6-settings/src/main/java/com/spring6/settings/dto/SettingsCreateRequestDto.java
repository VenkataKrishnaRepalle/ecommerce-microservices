package com.spring6.settings.dto;

import com.spring6.common.enums.SettingCategory;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SettingsCreateRequestDto {
    @NotBlank
    @Size(min = 2, max = 45)
    private String keyName;

    @NotBlank
    private String valueFiled;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SettingCategory category;
}
