package com.pm.spring.ema.authentication.common.service;

import com.pm.spring.ema.authentication.common.dto.request.AccountCreateRequestDto;
import com.pm.spring.ema.authentication.common.dto.request.AuthenticationRequestDto;
import com.pm.spring.ema.authentication.common.dto.response.AuthenticationResponseDto;

public interface AuthenticationService {
     AuthenticationResponseDto register(AccountCreateRequestDto accountCreateRequestDto);
     AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto);
     AuthenticationResponseDto refreshToken(String authHeader);

//     void forgotPassword(String email, String password);
}
