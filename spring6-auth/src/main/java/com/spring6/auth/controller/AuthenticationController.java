package com.spring6.auth.controller;

import com.spring6.auth.dto.AuthenticationRequestDto;
import com.spring6.auth.dto.AuthenticationResponseDto;
import com.spring6.auth.dto.UserCreateRequestDto;
import com.spring6.common.dto.UserInfoResponseDto;
import com.spring6.auth.service.AuthenticationService;
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


    @GetMapping("/user-info")
//    @PreAuthorize("hasAnyRole()")
    public ResponseEntity<UserInfoResponseDto> getUserInfo() {

        UserInfoResponseDto userInfoResponseDto =  authenticationService.getUserInfo();
        return ResponseEntity.ok(userInfoResponseDto);

    }
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDto> register(@RequestBody UserCreateRequestDto request) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDto> authenticate(@RequestBody AuthenticationRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<AuthenticationResponseDto> refreshToken(HttpServletRequest request) throws IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        return ResponseEntity.ok(authenticationService.refreshToken(authHeader));
    }

}
