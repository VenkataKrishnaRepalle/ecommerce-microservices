package com.pm.spring.ema.ecommerce.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorFormat {
    private String code;
    private String message;
}

