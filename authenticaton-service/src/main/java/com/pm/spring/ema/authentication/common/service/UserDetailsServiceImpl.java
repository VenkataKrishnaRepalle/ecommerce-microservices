package com.pm.spring.ema.authentication.common.service;

import com.pm.spring.ema.authentication.common.model.dao.AccountDao;
import com.pm.spring.ema.authentication.common.model.entity.Account;
import com.pm.spring.ema.authentication.common.model.entity.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service("customUserDetailsService")
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountDao accountDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> userOptional = accountDao.findByUsername(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return new MyUserDetails(userOptional.get());

    }
}
