package com.pm.spring.ema.common.util.dto;

import java.util.UUID;

public record CustomerDetailsDto(UUID id, String email, String firstName, String lastName) {
}
