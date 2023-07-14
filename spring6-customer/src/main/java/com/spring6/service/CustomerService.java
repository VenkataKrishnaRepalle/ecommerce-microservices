package com.spring6.service;

import com.spring6.dto.*;
import com.spring6.enums.EnabledStatus;

import java.util.UUID;

public interface CustomerService {

    CustomerCreateResponseDto register(CustomerCreateRequestDto customerCreateRequestDto);

    void login(LoginDto loginDto) throws Exception;

    Enum<EnabledStatus> getIsEnabledStatus(UUID id);

    void OTPValidation(UUID id, String otp);

    void forgotPassword(String email, ForgotPasswordDto forgotPasswordDto);

    void changePassword(String email, ChangePasswordDto changePasswordDto);

    void isEmailExists(String email);

}
