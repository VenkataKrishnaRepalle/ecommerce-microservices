package com.pm.spring.ema.feign;

import com.pm.spring.ema.common.util.dto.OtpDto;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import com.pm.spring.ema.common.util.exception.CustomErrorDecoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@FeignClient(name = "mail-service", url = "http://localhost:1004", configuration = CustomErrorDecoder.class)
public interface MailServiceFeign {
    @PostMapping("/send-login-otp/{userId}")
    OtpDto sendOTP(@PathVariable @NotNull UUID userId);
}
