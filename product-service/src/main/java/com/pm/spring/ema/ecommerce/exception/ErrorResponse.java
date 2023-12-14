package com.spring6.ecommerce.exception;

import com.pm.spring.ema.ecommerce.exception.ErrorFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    private List<ErrorFormat> errors;
}
