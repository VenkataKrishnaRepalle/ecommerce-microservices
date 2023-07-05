package com.spring6.service;

import com.spring6.dto.CustomerCreateRequestDto;
import com.spring6.dto.CustomerCreateResponseDto;
import com.spring6.dto.LoginDto;
import com.spring6.enums.EnabledStatus;

import java.util.UUID;

public interface CustomerService {

    CustomerCreateResponseDto register(CustomerCreateRequestDto customerCreateRequestDto);

    void login(LoginDto loginDto) throws Exception;

    Enum<EnabledStatus> getIsEnabledStatus(UUID id);

    void OTPValidation(UUID id, String otp);


}
