package com.spring6.auth.model.dao;

import com.spring6.auth.model.entity.Account;

import java.util.Optional;

public interface AccountDao {
    Optional<Account> findByUsername(String username);

    Account save(Account account);

    Optional<Account> findByEmail(String email);
}
