package com.spring6.service;

import com.spring6.dto.CustomerCreateRequestDto;
import com.spring6.dto.CustomerCreateResponseDto;

public interface CustomerService {

    CustomerCreateResponseDto register(CustomerCreateRequestDto customerCreateRequestDto);
}
