package com.pm.spring.ema.mapper;

import com.pm.spring.ema.dto.*;
import com.pm.spring.ema.model.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer customerFindResponseDtoToCustomer(CustomerFindResponseDto customerFindResponseDto);
    CustomerCreateResponseDto customerToCustomerCreateResponseDto(Customer customer);

    CustomerDto customerCreateRequestDtoToCustomerRegisterDto(CustomerCreateRequestDto customerCreateRequestDto);

    Customer cutomerRegisterDtoToCustomer(CustomerDto customerRegisterDto);

    CustomerDto customerToCustomerRegisterDto(Customer customer);

    CustomerFindResponseDto customerToCustomerFindResponseDto(Customer customer);

    LoginDto customerToLoginDto(Customer customer);
}
