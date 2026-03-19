package com.pm.spring.ema.authservice.service.impl;

import com.pm.spring.ema.authservice.dao.AuthDao;
import com.pm.spring.ema.authservice.dto.LoginDto;
import com.pm.spring.ema.authservice.kafka.MailProducer;
import com.pm.spring.ema.authservice.security.JwtTokenProvider;
import com.pm.spring.ema.authservice.service.AuthService;
import com.pm.spring.ema.common.util.dto.ApiResponse;
import com.pm.spring.ema.common.util.dto.CustomerDetailsDto;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

  private final AuthDao authDao;

  private final JwtTokenProvider jwtTokenProvider;

  private final PasswordEncoder passwordEncoder;

  private final MailProducer mailProducer;

  @Override
  public ApiResponse<?> login(LoginDto loginDto, HttpServletResponse response) {
    var customer = authDao.login(loginDto.getEmail());
    if (customer == null) {
      return ApiResponse.error("Invalid Email for login");
    }

    if (!passwordEncoder.matches(loginDto.getPassword(), customer.getPassword())) {
      return ApiResponse.error("Invalid Password for login");
    }

    Authentication authentication =
        new UsernamePasswordAuthenticationToken(
            String.valueOf(customer.getUuid()), null, Collections.emptyList());

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtTokenProvider.generateToken(authentication);

    Thread thread =
        new Thread(
            () ->
                mailProducer.sendLoginOtp(
                    new CustomerDetailsDto(
                        customer.getUuid(),
                        customer.getEmail(),
                        customer.getFirstName(),
                        customer.getLastName())));
    thread.start();

    return ApiResponse.success(token, "Login successful");
  }
}
