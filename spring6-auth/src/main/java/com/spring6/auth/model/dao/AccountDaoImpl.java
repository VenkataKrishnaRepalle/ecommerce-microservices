package com.spring6.auth.model.dao;

import com.spring6.auth.model.entity.Account;
import com.spring6.auth.model.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AccountDaoImpl implements AccountDao {
    private final AccountRepository accountRepository;

    @Override
    public Optional<Account> findByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account save(Account account) {

        return accountRepository.save(account);

    }

    @Override
    public Optional<Account> findByEmail(String email) {

        return accountRepository.findByEmail(email);
    }
}
