package com.pm.spring.ema.authentication.common.model.dao;

import com.pm.spring.ema.authentication.common.model.entity.Token;
import com.pm.spring.ema.authentication.common.model.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class TokenDaoImpl implements TokenDao {
    private final TokenRepository tokenRepository;

    @Override
    public Optional<Token> findByToken(String jwt) {
        return tokenRepository.findByToken(jwt);
    }

    @Override
    public void save(Token token) {
        tokenRepository.save(token);
    }

    @Override
    public List<Token> findAllValidTokenByUser(UUID id) {
        return tokenRepository.findAllValidTokenByUser(id);
    }

    @Override
    public void saveAll(List<Token> validUserTokens) {
        tokenRepository.saveAll(validUserTokens);
    }
}
