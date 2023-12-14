package com.pm.spring.ema.authentication.common.service;

import com.pm.spring.ema.authentication.common.model.entity.Token;

import java.util.Optional;

public interface DBTokenService {
    Optional<Token> getByToken(String jwt);
}
