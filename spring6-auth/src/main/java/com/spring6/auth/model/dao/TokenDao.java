package com.spring6.auth.model.dao;

import com.spring6.auth.model.entity.Token;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TokenDao {

    Optional<Token> findByToken(String jwt);

    void save(Token token);

    List<Token> findAllValidTokenByUser(UUID id);

    void saveAll(List<Token> validUserTokens);
}
