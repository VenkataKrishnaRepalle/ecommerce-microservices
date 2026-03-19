package com.pm.spring.ema.authservice.service;

import com.pm.spring.ema.authservice.dto.LoginDto;
import com.pm.spring.ema.common.util.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {
  ApiResponse<?> login(LoginDto loginDto, HttpServletResponse response);
}
