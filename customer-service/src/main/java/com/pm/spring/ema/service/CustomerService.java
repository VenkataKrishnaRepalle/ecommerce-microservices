package com.pm.spring.ema.service;

import com.pm.spring.ema.common.util.dto.ApiResponse;
import com.pm.spring.ema.common.util.dto.CustomerDetailsDto;
import com.pm.spring.ema.common.util.dto.CustomerDto;
import com.pm.spring.ema.common.util.dto.EnabledStatus;
import com.pm.spring.ema.dto.*;
import java.util.UUID;

public interface CustomerService {

  ApiResponse<CustomerDto> getById(UUID userId);

  ApiResponse<CustomerDetailsDto> getCustomerDetails(UUID userId);

  ApiResponse<CustomerDto> register(CustomerDto customerDto);

  ApiResponse<Void> login(LoginDto loginDto) throws Exception;

  Enum<EnabledStatus> getIsEnabledStatus(UUID id);

  ApiResponse<Void> forgotPassword(ForgotPasswordDto forgotPasswordDto);

  ApiResponse<Void> changePassword(ChangePasswordDto changePasswordDto);

  ApiResponse<Void> isEmailExists(String email);
}
