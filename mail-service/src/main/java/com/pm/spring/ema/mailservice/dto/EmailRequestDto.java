package com.pm.spring.ema.mailservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EmailRequestDto {

  private EmailRecipientDetailsDto sender;

  private List<EmailRecipientDetailsDto> to;

  private String subject;

  private String htmlContent;
}
