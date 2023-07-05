package com.spring6.controller;

import com.spring6.dto.CustomerCreateRequestDto;
import com.spring6.dto.CustomerCreateResponseDto;
import com.spring6.dto.LoginDto;
import com.spring6.enums.EnabledStatus;
import com.spring6.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("register")
    public ResponseEntity<CustomerCreateResponseDto> register(@RequestBody CustomerCreateRequestDto customerCreateRequestDto) {
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

    @GetMapping("OTPValidation/{id}")
    public ResponseEntity<HttpStatus> OTPValidation(@PathVariable UUID id, @RequestParam String OTP) {
        customerService.OTPValidation(id,OTP);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("forgotPasswod/{email}")
    public ResponseEntity<HttpStatus> forgotPassword(@PathVariable String email,
                                                     @RequestParam("password") String password) {
        customerService.forgotPassword(email,password);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("changePassword/{email}")
    public ResponseEntity<HttpHeaders> changePassword(@PathVariable String email,
                                                      @RequestParam("password") String password,
                                                      @RequestParam("reEnterPassword") String reEnterPassword,
                                                      @RequestParam("newPassword") String newPassword) {
        if(password.equals(reEnterPassword)) {
            customerService.changePassword(email, password, newPassword);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
