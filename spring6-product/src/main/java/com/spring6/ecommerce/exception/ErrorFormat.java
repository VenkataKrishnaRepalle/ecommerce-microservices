package com.spring6.ecommerce.exception;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorFormat {
    private String code;
    private String message;
}

