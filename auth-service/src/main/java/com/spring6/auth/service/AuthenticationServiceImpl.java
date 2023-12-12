package com.pm.spring.ema.auth.service;

import com.pm.spring.ema.auth.dto.mapper.AccountMapper;
import com.pm.spring.ema.auth.dto.request.AuthenticationRequestDto;
import com.pm.spring.ema.auth.dto.request.AccountCreateRequestDto;
import com.pm.spring.ema.auth.dto.response.AuthenticationResponseDto;
import com.pm.spring.ema.auth.exception.UserNotFoundException;
import com.pm.spring.ema.auth.model.dao.AccountDao;
import com.pm.spring.ema.auth.model.dao.TokenDao;
import com.pm.spring.ema.auth.model.entity.Account;
import com.pm.spring.ema.common.dto.UserInfoResponseDto;
import com.pm.spring.ema.auth.model.entity.MyUserDetails;
import com.pm.spring.ema.auth.model.entity.Token;
import com.pm.spring.ema.auth.model.enums.TokenType;
import com.pm.spring.ema.auth.exception.InvalidTokenException;
import com.pm.spring.ema.auth.exception.UserEmailAlreadyExistException;
import com.pm.spring.ema.auth.exception.UserNameAlreadyExistException;
import com.pm.spring.ema.auth.util.TraceIdHolder;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AccountDao accountDao;
    private final TokenDao tokenDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AccountMapper accountMapper;

    @Override
    public AuthenticationResponseDto register(AccountCreateRequestDto accountCreateRequestDto) {

        log.info("UserService:createUser execution started.");
        log.debug("UserService:createUser traceId: {} , userCreateRequestDto: {}", TraceIdHolder.getTraceId(), accountCreateRequestDto);

        if (isUsernameExist(accountCreateRequestDto.getUsername())) {
            log.error("UserService:createUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4506, accountCreateRequestDto.getUsername()));
            throw new UserNameAlreadyExistException(ErrorCodes.E4512, accountCreateRequestDto.getUsername());
        }

        if (isEmailExist(accountCreateRequestDto.getEmail())) {
            log.error("UserService:createUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4509, accountCreateRequestDto.getEmail()));
            throw new UserEmailAlreadyExistException(ErrorCodes.E4513, accountCreateRequestDto.getEmail());
        }

        Account account = accountMapper.userCreateRequestDtoToUser(accountCreateRequestDto);

        account.setPassword(passwordEncoder.encode(account.getPassword()));
        Account accountCreated = accountDao.save(account);

        UserDetails userDetails = new MyUserDetails(accountCreated);

        var savedUser = accountDao.save(account);

        var jwtToken = jwtService.generateToken(userDetails);
        var refreshToken = jwtService.generateRefreshToken(userDetails);

        saveUserToken(savedUser, jwtToken);

        AuthenticationResponseDto authenticationResponseDto = AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();

        log.debug("UserService:createUser traceId: {}, response: {}", TraceIdHolder.getTraceId(), authenticationResponseDto);
        log.info("UserService:createUser execution ended.");

        return authenticationResponseDto;

    }

    @Override
    public AuthenticationResponseDto authenticate(AuthenticationRequestDto authenticationRequestDto) {

        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequestDto.getUsername(), authenticationRequestDto.getPassword()));

        Optional<Account> userOptional = accountDao.findByUsername(authenticationRequestDto.getUsername());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User name not exist");
        }
        Account account = userOptional.get();

        UserDetails userDetails = new MyUserDetails(account);

        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        revokeAllUserTokens(account);
        saveUserToken(account, jwtToken);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponseDto refreshToken(final String authHeader) {
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid token");
        }
        refreshToken = authHeader.substring(7);
        username = jwtService.extractUsername(refreshToken);
        if (username == null) {
            throw new UsernameNotFoundException("User name not exist");

        }
        Optional<Account> optionalUser = accountDao.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User name not exist");
        }
        Account account = optionalUser.get();
        UserDetails userDetails = new MyUserDetails(account);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new InvalidTokenException("Invalid token");

        }
        var accessToken = jwtService.generateToken(userDetails);

        revokeAllUserTokens(account);
        saveUserToken(account, accessToken);

        return AuthenticationResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();


    }



//    @Override
//    public void forgotPassword(String email, String password) {
//        CustomerRegisterDto customer = customerMapper.customerToCustomerRegisterDto(customerRepository.findByEmail(email));
//        if(customer != null) {
//            password = passwordEncoder.encode(password);
//            customerRepository.forgotPassword(email,password);
//        }
//    }

    private void saveUserToken(Account account, String jwtToken) {
        var token = Token.builder()
                .account(account)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenDao.save(token);
    }

    private void revokeAllUserTokens(Account account) {
        List<Token> validUserTokens = tokenDao.findAllValidTokenByUser(account.getId());

        if (validUserTokens.isEmpty()) return;

        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });

        tokenDao.saveAll(validUserTokens);
    }

    private Boolean isUsernameExist(String username) {

        Optional<Account> optionalUser = accountDao.findByUsername(username);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean isEmailExist(String email) {

        Optional<Account> optionalUser = accountDao.findByEmail(email);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

}
