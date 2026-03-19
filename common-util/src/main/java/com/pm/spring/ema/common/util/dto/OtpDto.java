package com.pm.spring.ema.common.util.dto;

import java.util.Date;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OtpDto {
  private UUID id;

  private UUID userUuid;

  private OtpStatus status;

  private OtpType type;

  private Long otpNumber;

  private Date createdTime;

  public enum OtpStatus {
    ACTIVE,
    EXPIRED,
    USED
  }

  public enum OtpType {
    LOGIN_OTP,
    FORGOT_PASSWORD_OTP
  }
}
