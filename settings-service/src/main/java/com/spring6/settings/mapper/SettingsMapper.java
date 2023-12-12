package com.pm.spring.ema.settings.mapper;

import com.pm.spring.ema.common.dto.SettingsFindResponseDto;
import com.pm.spring.ema.settings.dto.SettingsCreateRequestDto;
import com.pm.spring.ema.settings.dto.SettingsCreateResponseDto;
import com.pm.spring.ema.settings.dto.SettingsUpdateRequestDto;
import com.pm.spring.ema.settings.dto.SettingsUpdateResponseDto;
import com.pm.spring.ema.settings.entity.Settings;
import org.mapstruct.Mapper;


@Mapper
public interface SettingsMapper {
    SettingsFindResponseDto settingsToSettingsFindResponseDto(Settings settings);
    Settings settingsUpdateRequestDtoToSettings (SettingsUpdateRequestDto settingsUpdateRequestDto);
    SettingsUpdateResponseDto settingsToSettingsUpdateResponseDto(Settings settings);
    Settings settingsCreateRequestDtoToSettings(SettingsCreateRequestDto settingsCreateRequestDto);
    SettingsCreateResponseDto  settingsToSettingsCreateResponseDto(Settings settings);


}
