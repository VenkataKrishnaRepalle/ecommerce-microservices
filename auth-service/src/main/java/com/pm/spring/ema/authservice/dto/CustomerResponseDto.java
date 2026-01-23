package com.pm.spring.ema.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {

    private UUID uuid;

    private String email;

    private String password;
}
