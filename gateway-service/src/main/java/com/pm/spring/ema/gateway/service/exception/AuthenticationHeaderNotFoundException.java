package com.pm.spring.ema.gateway.service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Auth header not found!")
public class AuthenticationHeaderNotFoundException extends RuntimeException {
  public AuthenticationHeaderNotFoundException() {}

  public AuthenticationHeaderNotFoundException(String message) {
    super(message);
  }

  public AuthenticationHeaderNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public AuthenticationHeaderNotFoundException(Throwable cause) {
    super(cause);
  }

  public AuthenticationHeaderNotFoundException(
      String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }
}
