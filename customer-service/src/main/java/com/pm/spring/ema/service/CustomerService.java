package com.pm.spring.ema.service;

import com.pm.spring.ema.dto.*;
import com.pm.spring.ema.modal.EnabledStatus;

import java.util.UUID;

public interface CustomerService {

    CustomerDto getById(UUID userId);

    CustomerDto register(CustomerDto customerDto);

    void login(LoginDto loginDto) throws Exception;

    Enum<EnabledStatus> getIsEnabledStatus(UUID id);

    void forgotPassword(ForgotPasswordDto forgotPasswordDto);

    void changePassword(ChangePasswordDto changePasswordDto);

    void isEmailExists(String email);

}
