package com.pm.spring.ema.authentication.common.model.dao;

import com.pm.spring.ema.authentication.common.model.entity.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenDao {

    Optional<Token> findByToken(String jwt);

    void save(Token token);

    List<Token> findAllValidTokenByUser(UUID id);

    void saveAll(List<Token> validUserTokens);
}
