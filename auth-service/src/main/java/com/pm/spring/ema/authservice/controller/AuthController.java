package com.pm.spring.ema.authservice.controller;

import com.pm.spring.ema.authservice.dto.LoginDto;
import com.pm.spring.ema.authservice.service.AuthService;
import com.pm.spring.ema.common.util.dto.ApiResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {

  private final AuthService authService;

  @PostMapping("/login")
  public ResponseEntity<ApiResponse<?>> login(
      @RequestBody LoginDto loginDto, HttpServletResponse response) {
    return new ResponseEntity<>(authService.login(loginDto, response), HttpStatus.OK);
  }
}
