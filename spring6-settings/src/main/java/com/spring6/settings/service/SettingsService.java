package com.spring6.settings.service;

import com.spring6.common.dto.SettingsFindResponseDto;
import com.spring6.settings.dto.SettingsCreateRequestDto;
import com.spring6.settings.dto.SettingsCreateResponseDto;
import com.spring6.settings.dto.SettingsUpdateRequestDto;
import com.spring6.settings.dto.SettingsUpdateResponseDto;

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
