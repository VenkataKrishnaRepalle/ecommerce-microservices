package com.spring6.auth.service;

import com.spring6.auth.dto.AuthenticationRequestDto;
import com.spring6.auth.dto.AuthenticationResponseDto;
import com.spring6.auth.dto.UserCreateRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthenticationService {
     AuthenticationResponseDto register(UserCreateRequestDto userCreateRequestDto);
     AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
     AuthenticationResponseDto refreshToken(String authHeader);
//     void forgotPassword(String email, String password);
}
