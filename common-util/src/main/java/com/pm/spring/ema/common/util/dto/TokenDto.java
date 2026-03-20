package com.pm.spring.ema.common.util.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TokenDto {

    private String token;

    private Instant expiryTime;

    public boolean isExpired() {
        return Instant.now().isAfter(expiryTime.minusSeconds(15));
    }
}
