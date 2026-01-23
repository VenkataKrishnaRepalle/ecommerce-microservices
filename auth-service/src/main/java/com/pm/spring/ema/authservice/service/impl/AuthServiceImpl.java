package com.pm.spring.ema.authservice.service.impl;

import com.pm.spring.ema.authservice.dao.AuthDao;
import com.pm.spring.ema.authservice.dto.LoginDto;
import com.pm.spring.ema.authservice.security.JwtTokenProvider;
import com.pm.spring.ema.authservice.service.AuthService;
import com.pm.spring.ema.common.util.dto.ApiResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthDao authDao;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.jwt-expiration-milliseconds}")
    private int jwtExpiryTime;

    @Override
    public ApiResponse<?> login(LoginDto loginDto, HttpServletResponse response) {
        var customer = authDao.login(loginDto.getEmail());
        if (customer == null) {
            return ApiResponse.error("Invalid Email for login");
        }

        if (!customer.getPassword().equals(loginDto.getPassword())) {
            return ApiResponse.error("Invalid Password for login");
        }

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                String.valueOf(customer.getUuid()),
                null,
                Collections.emptyList()
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);

        addAuthCookie(response, token, jwtExpiryTime);
        return ApiResponse.success("Login successful");
    }

    private void addAuthCookie(HttpServletResponse response, String value, int maxAge) {
        Cookie cookie = new Cookie("token", value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
    }
}
