package com.pm.spring.ema.auth.controller;

import com.pm.spring.ema.auth.service.UserInfoService;
import com.pm.spring.ema.common.dto.UserInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api/v1")
@RequiredArgsConstructor
@RestController
public class UserInfoController {
    private final UserInfoService userInfoService;

    @GetMapping("user-info")
//    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CUSTOMER')")
    public ResponseEntity<UserInfoResponseDto> getUserInfo() {

        UserInfoResponseDto userInfoResponseDto = userInfoService.getUserInfo();
        return ResponseEntity.ok(userInfoResponseDto);

    }
}
