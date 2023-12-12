package com.pm.spring.ema.auth.service;

import com.pm.spring.ema.auth.model.entity.Token;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;

import java.util.Optional;

public interface DBTokenService {
    Optional<Token> getByToken(String jwt);
}
