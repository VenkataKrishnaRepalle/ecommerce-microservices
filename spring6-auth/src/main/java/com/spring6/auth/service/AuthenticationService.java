package com.spring6.auth.service;

import com.spring6.auth.dto.request.AccountCreateRequestDto;
import com.spring6.auth.dto.request.AuthenticationRequestDto;
import com.spring6.auth.dto.response.AuthenticationResponseDto;
import com.spring6.common.dto.UserInfoResponseDto;

public interface AuthenticationService {
     AuthenticationResponseDto register(AccountCreateRequestDto accountCreateRequestDto);
     AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
     AuthenticationResponseDto refreshToken(String authHeader);

    UserInfoResponseDto getUserInfo();
//     void forgotPassword(String email, String password);
}
