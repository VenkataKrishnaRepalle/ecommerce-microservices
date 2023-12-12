package com.pm.spring.ema.mailservice.service;

import com.pm.spring.ema.mailservice.model.enums.OtpType;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

public interface MailService {
    void otpValidation(UUID userId, Long otp, OtpType type);

    void createOtp(UUID userId, OtpType type);

    void sendLoginOtp(UUID userId) throws MessagingException, UnsupportedEncodingException;

    void sendForgotPasswordOtp(UUID userId) throws MessagingException, UnsupportedEncodingException;
}
