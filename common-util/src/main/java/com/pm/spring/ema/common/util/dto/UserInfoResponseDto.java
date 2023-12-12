package com.pm.spring.ema.common.dto;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class UserInfoResponseDto {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;

}
