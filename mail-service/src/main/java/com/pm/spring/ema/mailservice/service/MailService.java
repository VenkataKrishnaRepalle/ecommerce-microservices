package com.pm.spring.ema.mailservice.service;

import com.pm.spring.ema.common.util.dto.CustomerDetailsDto;
import com.pm.spring.ema.mailservice.model.Otp;
import com.pm.spring.ema.mailservice.model.OtpType;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

public interface MailService {
  void otpValidation(UUID userId, Long otp, OtpType type);

  Otp createOtp(UUID userId, OtpType type);

  void sendLoginOtp(UUID userId) throws MessagingException, UnsupportedEncodingException;

  void sendLoginMail(CustomerDetailsDto customerDetailsDto)
      throws MessagingException, UnsupportedEncodingException;

  void sendForgotPasswordOtp(UUID userId) throws MessagingException, UnsupportedEncodingException;

  void sendForgotPasswordMail(CustomerDetailsDto customerDetailsDto)
      throws MessagingException, UnsupportedEncodingException;
}
