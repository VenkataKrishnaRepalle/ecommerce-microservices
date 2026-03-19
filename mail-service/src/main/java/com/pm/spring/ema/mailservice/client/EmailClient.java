package com.pm.spring.ema.mailservice.client;

import com.pm.spring.ema.mailservice.dto.EmailRequestDto;
import com.pm.spring.ema.mailservice.dto.EmailResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "email-client", url = "${default.send.email.url}")
public interface EmailClient {

  @PostMapping("/email")
  EmailResponseDto sendEmail(
      @RequestHeader("api-key") String apiKey, @RequestBody EmailRequestDto requestDto);
}
