package com.pm.spring.ema.controller;

import com.pm.spring.ema.dto.*;
import com.pm.spring.ema.modal.EnabledStatus;
import com.pm.spring.ema.service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("register")
    public ResponseEntity<CustomerDto> register(@RequestBody CustomerDto customerCreateRequestDto) {
        return new ResponseEntity<>(customerService.register(customerCreateRequestDto), HttpStatus.CREATED);
    }

    @PostMapping("login")
    public ResponseEntity<HttpStatus> login(@RequestBody @Valid LoginDto loginDto) throws Exception {
        customerService.login(loginDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("isEnabledStatus/{id}")
    public Enum<EnabledStatus> getIsEnabledStatus(@PathVariable UUID id) {
        return customerService.getIsEnabledStatus(id);
    }

    @PostMapping("{email}")
    public ResponseEntity<HttpStatus> isEmailExists(@PathVariable String email) {
        customerService.isEmailExists(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("forgotPasswod/{email}")
    public ResponseEntity<HttpStatus> forgotPassword(@PathVariable String email,
                                                     @RequestBody ForgotPasswordDto forgotPasswordDto) {
        customerService.forgotPassword(email, forgotPasswordDto);
            return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("changePassword/{email}")
    public ResponseEntity<HttpStatus> changePassword(@PathVariable String email,
                                                     @RequestBody ChangePasswordDto changePasswordDto
                                                     ) {
        customerService.changePassword(email, changePasswordDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
