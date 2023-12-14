package com.pm.spring.ema.authentication.common.model.dao;

import com.pm.spring.ema.authentication.common.model.entity.Account;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findByUsername(String username);

    Account save(Account account);

    Optional<Account> findByEmail(String email);
}
