package com.spring6.auth.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationRequestDto {
    private String username;
    private String password;
}
