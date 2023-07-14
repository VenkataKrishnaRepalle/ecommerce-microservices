package com.spring6.settings.service;

import com.spring6.common.dto.SettingsFindResponseDto;
import com.spring6.settings.dto.SettingsCreateRequestDto;
import com.spring6.settings.dto.SettingsCreateResponseDto;
import com.spring6.settings.dto.SettingsUpdateRequestDto;
import com.spring6.settings.dto.SettingsUpdateResponseDto;
import com.spring6.settings.entity.Settings;
import com.spring6.settings.exception.SettingsNotFoundException;
import com.spring6.settings.mapper.SettingsMapper;
import com.spring6.settings.repository.SettingsRepository;
import com.spring6.settings.utils.TraceIdHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SettingsServiceImpl implements SettingsService {
    private final SettingsRepository settingsRepository;
    private final SettingsMapper settingsMapper;

    @Override
    public List<SettingsFindResponseDto> getAllSettings() {
        log.info("SettingsService:getAllSettings execution started.");
        log.debug("SettingsService:getAllSettings traceId: {}", TraceIdHolder.getTraceId());


        List<SettingsFindResponseDto> settingsFindResponseDtoList = settingsRepository.findAll()
                .stream()
                .map(settingsMapper::settingsToSettingsFindResponseDto)
                .toList();
        log.debug("SettingsService:getAllSettings traceId: {}, response {} ", TraceIdHolder.getTraceId(), settingsFindResponseDtoList);
        log.info("SettingsService:getAllSettings execution ended.");

        return settingsFindResponseDtoList;
    }

    @Override
    public SettingsFindResponseDto getSettingsById(UUID id) throws SettingsNotFoundException {

        log.info("SettingsService:getSettingsById execution started.");
        log.debug("SettingsService:getSettingsById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Settings> optionalSettings = settingsRepository.findById(id);
        if(optionalSettings.isEmpty()) {
            log.error("SettingsService:getSettingsById traceId: {}, errorMessage: Brand Not found", TraceIdHolder.getTraceId());
            log.info("SettingsService:getSettingsById execution ended.");
        }
            SettingsFindResponseDto settingsFindResponseDto = settingsMapper.settingsToSettingsFindResponseDto(optionalSettings.get());
            return settingsFindResponseDto;
    }

    @Override
    public SettingsCreateResponseDto createSettings(SettingsCreateRequestDto settingsCreateRequestDto) {
        return null;
    }

    @Override
    public SettingsUpdateResponseDto updateSettings(UUID id, SettingsUpdateRequestDto settingsUpdateRequestDto) {
        return null;
    }

    @Override
    public void deleteSettingsById(UUID id) {

    }

    @Override
    public Boolean isSettingsExistByName(String keyName) {
        Optional<Settings> optionalSettings = settingsRepository.findByName(keyName);

        if (optionalSettings.isPresent()){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isSettingsExistById(UUID id) {
        Optional<Settings> optionalSettings = settingsRepository.findById(id);
        if(optionalSettings.isPresent()){
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }
}
