package com.pm.spring.ema.authservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.spring.ema.common.util.dto.CustomerDetailsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailProducer {

  private final KafkaTemplate<String, String> kafkaTemplate;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public void sendLoginOtp(CustomerDetailsDto customerDetailsDto) {
    try {
      kafkaTemplate.send("send-login-mail", objectMapper.writeValueAsString(customerDetailsDto));
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
