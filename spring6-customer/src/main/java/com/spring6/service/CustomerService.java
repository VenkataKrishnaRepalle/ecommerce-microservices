package com.spring6.service;

import com.spring6.dto.CustomerCreateRequestDto;
import com.spring6.dto.CustomerCreateResponseDto;
import com.spring6.dto.LoginDto;
import com.spring6.entity.EnabledStatus;
import org.springframework.http.ResponseEntity;

import java.util.UUID;

public interface CustomerService {

    CustomerCreateResponseDto register(CustomerCreateRequestDto customerCreateRequestDto);

    void login(LoginDto loginDto) throws Exception;

    Enum<EnabledStatus> getIsEnabledStatus(UUID id);

    void OTPValidation(UUID id, String otp);

    void forgotPassword(String email, String password);
}
