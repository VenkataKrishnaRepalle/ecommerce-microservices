package com.pm.spring.ema.settings.controller;

import com.pm.spring.ema.common.dto.SettingsFindResponseDto;
import com.pm.spring.ema.settings.service.SettingsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/setting")
@Tag(name = "Setting")
public class SettingsController {
    private final SettingsService settingsService;
    @GetMapping("list")
    public ResponseEntity<List<SettingsFindResponseDto>> getAllSettings() {

        List<SettingsFindResponseDto> categoryFindResponseDtoList = settingsService.getAllSettings();


        return ResponseEntity.ok()
                .body(categoryFindResponseDtoList);
    }
    @GetMapping("{id}")
    public  ResponseEntity<SettingsFindResponseDto> getgetSettingsById(@PathVariable final UUID id){
        SettingsFindResponseDto settingsFindResponseDto = settingsService.getSettingsById(id);
        return ResponseEntity.ok()
                .body(settingsFindResponseDto);

    }

}
