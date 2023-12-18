package com.pm.spring.ema.auth.service.common.service;

import com.pm.spring.ema.auth.service.common.model.entity.Token;

import java.util.Optional;

public interface DBTokenService {
    Optional<Token> getByToken(String jwt);
}
