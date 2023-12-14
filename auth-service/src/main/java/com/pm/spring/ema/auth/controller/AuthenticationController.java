package com.pm.spring.ema.auth.controller;

import com.pm.spring.ema.auth.dto.request.AuthenticationRequestDto;
import com.pm.spring.ema.auth.dto.response.AuthenticationResponseDto;
import com.pm.spring.ema.auth.dto.request.AccountCreateRequestDto;
import com.pm.spring.ema.common.dto.UserInfoResponseDto;
import com.pm.spring.ema.auth.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody AccountCreateRequestDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(HttpServletRequest request) throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(authenticationService.refreshToken(authHeader));
    }

}
