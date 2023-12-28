package com.pm.spring.ema.auth.service.common.service;

import com.pm.spring.ema.auth.service.common.dto.response.UserInfoResponseDto;
import com.pm.spring.ema.auth.service.common.exception.UserNotFoundException;
import com.pm.spring.ema.auth.service.common.dto.mapper.AccountMapper;
import com.pm.spring.ema.auth.service.common.model.dao.AccountDao;
import com.pm.spring.ema.auth.service.common.model.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
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
