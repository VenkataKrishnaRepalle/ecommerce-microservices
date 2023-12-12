package com.pm.spring.ema.settings.service;

import com.pm.spring.ema.common.dto.SettingsFindResponseDto;
import com.pm.spring.ema.settings.dto.SettingsCreateRequestDto;
import com.pm.spring.ema.settings.dto.SettingsCreateResponseDto;
import com.pm.spring.ema.settings.dto.SettingsUpdateRequestDto;
import com.pm.spring.ema.settings.dto.SettingsUpdateResponseDto;

import java.util.List;
import java.util.UUID;

public interface SettingsService {
    List<SettingsFindResponseDto> getAllSettings();

    SettingsFindResponseDto getSettingsById(UUID id);

    SettingsCreateResponseDto createSettings(SettingsCreateRequestDto settingsCreateRequestDto);

    SettingsUpdateResponseDto updateSettings(UUID id, SettingsUpdateRequestDto settingsUpdateRequestDto);

    void deleteSettingsById(UUID id);

    Boolean isSettingsExistByName(String keyName);
    Boolean isSettingsExistById(UUID id);
}
