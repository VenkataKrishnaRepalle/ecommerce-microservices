package com.pm.spring.ema.mailservice.service;

import com.pm.spring.ema.common.util.dto.CustomerDetailsDto;
import com.pm.spring.ema.mailservice.model.Otp;
import com.pm.spring.ema.mailservice.model.OtpType;
import java.util.UUID;

public interface MailService {
  void otpValidation(UUID userId, Long otp, OtpType type);

  Otp createOtp(UUID userId, OtpType type);

  void sendLoginOtp(UUID userId);

  void sendLoginMail(CustomerDetailsDto customerDetailsDto);

  void sendForgotPasswordOtp(UUID userId);

  void sendForgotPasswordMail(CustomerDetailsDto customerDetailsDto);
}
