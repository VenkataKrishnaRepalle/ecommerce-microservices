package com.pm.spring.ema.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ForgotPasswordDto {

  @Email @NotNull private String email;

  @NotNull private String password;

  @NotNull private String confirmPassword;
}
