package com.pm.spring.ema.controller;

import com.pm.spring.ema.common.util.dto.ApiResponse;
import com.pm.spring.ema.common.util.dto.CustomerDetailsDto;
import com.pm.spring.ema.common.util.dto.CustomerDto;
import com.pm.spring.ema.common.util.dto.EnabledStatus;
import com.pm.spring.ema.dto.*;
import com.pm.spring.ema.service.CustomerService;
import jakarta.validation.Valid;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("customer")
@RequiredArgsConstructor
public class CustomerController {

  private final CustomerService customerService;

  @PostMapping("register")
  public ResponseEntity<ApiResponse<CustomerDto>> register(
      @RequestBody CustomerDto customerCreateRequestDto) {
    return new ResponseEntity<>(
        customerService.register(customerCreateRequestDto), HttpStatus.CREATED);
  }

  @PostMapping("login")
  public ResponseEntity<ApiResponse<Void>> login(@RequestBody @Valid LoginDto loginDto)
      throws Exception {
    return new ResponseEntity<>(customerService.login(loginDto), HttpStatus.OK);
  }

  @GetMapping("get/{userId}")
  public ResponseEntity<ApiResponse<CustomerDto>> getCustomer(@PathVariable UUID userId) {
    return new ResponseEntity<>(customerService.getById(userId), HttpStatus.OK);
  }

  @GetMapping("getDetails/{userId}")
  public ResponseEntity<ApiResponse<CustomerDetailsDto>> getCustomerDetails(
      @PathVariable UUID userId) {
    return new ResponseEntity<>(customerService.getCustomerDetails(userId), HttpStatus.OK);
  }

  @GetMapping("isEnabledStatus/{id}")
  public Enum<EnabledStatus> getIsEnabledStatus(@PathVariable UUID id) {
    return customerService.getIsEnabledStatus(id);
  }

  @PostMapping("{email}")
  public ResponseEntity<ApiResponse<Void>> isEmailExists(@PathVariable String email) {
    return new ResponseEntity<>(customerService.isEmailExists(email), HttpStatus.OK);
  }

  @PostMapping("forgotPasswod")
  public ResponseEntity<ApiResponse<Void>> forgotPassword(
      @RequestBody ForgotPasswordDto forgotPasswordDto) {
    return new ResponseEntity<>(customerService.forgotPassword(forgotPasswordDto), HttpStatus.OK);
  }

  @PostMapping("changePassword")
  public ResponseEntity<ApiResponse<Void>> changePassword(
      @RequestBody ChangePasswordDto changePasswordDto) {
    return new ResponseEntity<>(customerService.changePassword(changePasswordDto), HttpStatus.OK);
  }
}
