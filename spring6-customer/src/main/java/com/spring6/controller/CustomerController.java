package com.spring6.controller;

import com.spring6.dto.CustomerCreateRequestDto;
import com.spring6.dto.CustomerCreateResponseDto;
import com.spring6.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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

//    @GetMapping("login")
//    public ResponseEntity
}
