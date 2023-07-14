package com.spring6.settings.mapper;

import com.spring6.common.dto.SettingsFindResponseDto;
import com.spring6.settings.dto.SettingsCreateRequestDto;
import com.spring6.settings.dto.SettingsCreateResponseDto;
import com.spring6.settings.dto.SettingsUpdateRequestDto;
import com.spring6.settings.dto.SettingsUpdateResponseDto;
import com.spring6.settings.entity.Settings;
import org.mapstruct.Mapper;


@Mapper
public interface SettingsMapper {
    SettingsFindResponseDto settingsToSettingsFindResponseDto(Settings settings);
    Settings settingsUpdateRequestDtoToSettings (SettingsUpdateRequestDto settingsUpdateRequestDto);
    SettingsUpdateResponseDto settingsToSettingsUpdateResponseDto(Settings settings);
    Settings settingsCreateRequestDtoToSettings(SettingsCreateRequestDto settingsCreateRequestDto);
    SettingsCreateResponseDto  settingsToSettingsCreateResponseDto(Settings settings);


}
