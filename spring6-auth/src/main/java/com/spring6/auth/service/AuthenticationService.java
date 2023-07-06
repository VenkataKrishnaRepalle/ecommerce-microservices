package com.spring6.auth.service;

import com.spring6.auth.dto.AuthenticationRequestDto;
import com.spring6.auth.dto.AuthenticationResponseDto;
import com.spring6.auth.dto.UserCreateRequestDto;
import com.spring6.common.dto.UserInfoResponseDto;

public interface AuthenticationService {
     AuthenticationResponseDto register(UserCreateRequestDto userCreateRequestDto);
     AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
     AuthenticationResponseDto refreshToken(String authHeader);

    UserInfoResponseDto getUserInfo();
//     void forgotPassword(String email, String password);
}
