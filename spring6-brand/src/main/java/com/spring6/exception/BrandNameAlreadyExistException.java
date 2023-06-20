package com.spring6.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Brand Name already exist")
public class BrandNameAlreadyExistException extends RuntimeException {
    public BrandNameAlreadyExistException(String message) {
        super(message);
    }
}
