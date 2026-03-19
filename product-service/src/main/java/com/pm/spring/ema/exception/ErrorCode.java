package com.pm.spring.ema.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ErrorCode {
  E1001("E0001", "Field {0} is required."),

  E1503("E1503", "Product already present with name : {0}");

  private final String code;
  private final String message;
}
