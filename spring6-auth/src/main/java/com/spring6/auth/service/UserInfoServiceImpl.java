package com.spring6.auth.service;

import com.spring6.auth.dto.mapper.AccountMapper;
import com.spring6.auth.exception.UserNotFoundException;
import com.spring6.auth.model.dao.AccountDao;
import com.spring6.auth.model.entity.Account;
import com.spring6.common.dto.UserInfoResponseDto;
import com.spring6.common.exeption.ErrorCodes;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserInfoServiceImpl implements UserInfoService {
    private final AccountDao accountDao;
    private final AccountMapper accountMapper;

    @Override
    public UserInfoResponseDto getUserInfo() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new UsernameNotFoundException(ErrorCodes.E4015);
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<Account> optionalUser = accountDao.findByUsername(userDetails.getUsername());

        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException(ErrorCodes.E4016);
        }
        Account account = optionalUser.get();

        return accountMapper.userToUserProfileResponseDto(account);
    }
}
