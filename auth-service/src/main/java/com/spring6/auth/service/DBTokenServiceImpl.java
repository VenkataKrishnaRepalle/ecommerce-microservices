package com.pm.spring.ema.auth.service;

import com.pm.spring.ema.auth.model.dao.TokenDao;
import com.pm.spring.ema.auth.model.entity.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class DBTokenServiceImpl implements DBTokenService {

    private final TokenDao tokenDao;

    @Override
    public Optional<Token> getByToken(String jwt) {
        return tokenDao.findByToken(jwt);
    }
}
