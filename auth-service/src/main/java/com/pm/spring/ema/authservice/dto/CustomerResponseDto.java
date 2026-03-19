package com.pm.spring.ema.authservice.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {

  private UUID uuid;

  private String firstName;

  private String lastName;

  private String email;

  private String password;
}
