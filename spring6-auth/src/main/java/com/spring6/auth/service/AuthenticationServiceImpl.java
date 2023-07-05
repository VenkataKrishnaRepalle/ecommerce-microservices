package com.spring6.auth.service;

import com.spring6.auth.dto.AuthenticationRequestDto;
import com.spring6.auth.dto.AuthenticationResponseDto;
import com.spring6.auth.dto.UserCreateRequestDto;
import com.spring6.auth.entity.MyUserDetails;
import com.spring6.auth.entity.Token;
import com.spring6.auth.entity.User;
import com.spring6.auth.enums.TokenType;
import com.spring6.auth.exception.InvalidTokenException;
import com.spring6.auth.exception.UserEmailAlreadyExistException;
import com.spring6.auth.exception.UserNameAlreadyExistException;
import com.spring6.auth.util.TraceIdHolder;
import com.spring6.auth.mapper.UserMapper;
import com.spring6.auth.repository.TokenRepository;
import com.spring6.auth.repository.UserRepository;
import com.spring6.common.exeption.ErrorCodes;
import com.spring6.common.exeption.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;

    @Override
    public AuthenticationResponseDto register(UserCreateRequestDto userCreateRequestDto) {

        log.info("UserService:createUser execution started.");
        log.debug("UserService:createUser traceId: {} , userCreateRequestDto: {}", TraceIdHolder.getTraceId(), userCreateRequestDto);

        if (isUsernameExist(userCreateRequestDto.getUsername())) {
            log.error("UserService:createUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4506, userCreateRequestDto.getUsername()));
            throw new UserNameAlreadyExistException(ErrorCodes.E4512, userCreateRequestDto.getUsername());
        }

        if (isEmailExist(userCreateRequestDto.getEmail())) {
            log.error("UserService:createUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4509, userCreateRequestDto.getEmail()));
            throw new UserEmailAlreadyExistException(ErrorCodes.E4513, userCreateRequestDto.getEmail());
        }

        User user = userMapper.userCreateRequestDtoToUser(userCreateRequestDto);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userCreated = userRepository.save(user);

        UserDetails userDetails = new MyUserDetails(userCreated);

        var savedUser = userRepository.save(user);

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
                new UsernamePasswordAuthenticationToken(authenticationRequestDto.getEmail(), authenticationRequestDto.getPassword()));


        Optional<User> userOptional = userRepository.findByEmail(authenticationRequestDto.getEmail());
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User name not exist");
        }
        User user = userOptional.get();

        UserDetails userDetails = new MyUserDetails(user);

        String jwtToken = jwtService.generateToken(userDetails);
        String refreshToken = jwtService.generateRefreshToken(userDetails);

        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);

        return AuthenticationResponseDto.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    @Override
    public AuthenticationResponseDto refreshToken(final String authHeader) {
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid token");
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail == null) {
            throw new UsernameNotFoundException("User name not exist");

        }
        Optional<User> optionalUser = userRepository.findByEmail(userEmail);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User name not exist");
        }
        User user = optionalUser.get();
        UserDetails userDetails = new MyUserDetails(user);

        if (!jwtService.isTokenValid(refreshToken, userDetails)) {
            throw new InvalidTokenException("Invalid token");

        }
        var accessToken = jwtService.generateToken(userDetails);

        revokeAllUserTokens(user);
        saveUserToken(user, accessToken);

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

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

    private Boolean isUsernameExist(String username) {

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean isEmailExist(String email) {

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

}
