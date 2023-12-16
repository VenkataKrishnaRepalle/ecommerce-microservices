package com.pm.spring.ema.auth.service.common.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
