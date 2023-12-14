package com.pm.spring.ema.auth.service;

import com.pm.spring.ema.auth.dto.request.AccountCreateRequestDto;
import com.pm.spring.ema.auth.dto.request.AuthenticationRequestDto;
import com.pm.spring.ema.auth.dto.response.AuthenticationResponseDto;

public interface AuthenticationService {
     AuthenticationResponseDto register(AccountCreateRequestDto accountCreateRequestDto);
     AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
     AuthenticationResponseDto refreshToken(String authHeader);

//     void forgotPassword(String email, String password);
}
