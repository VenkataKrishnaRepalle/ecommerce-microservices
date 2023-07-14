package com.spring6.auth.service;

import com.spring6.auth.model.entity.Token;
import io.micrometer.core.instrument.binder.db.MetricsDSLContext;

import java.util.Optional;

public interface DBTokenService {
    Optional<Token> getByToken(String jwt);
}
