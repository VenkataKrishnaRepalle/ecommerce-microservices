package com.pm.spring.ema.mailservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.spring.ema.common.util.dto.CustomerDetailsDto;
import com.pm.spring.ema.mailservice.service.MailService;
import jakarta.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailConsumer {

  private final ObjectMapper objectMapper = new ObjectMapper();

  private final MailService mailService;

  @KafkaListener(topics = "send-login-mail", groupId = "mail-group")
  public void sendLoginMail(String message)
      throws JsonProcessingException, MessagingException, UnsupportedEncodingException {
    var details = objectMapper.readValue(message, CustomerDetailsDto.class);
    System.out.println("Received details: " + details);
    mailService.sendLoginMail(details);
  }
}
