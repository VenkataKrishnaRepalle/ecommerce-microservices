package com.spring6.mapper;

import com.spring6.dto.*;
import com.spring6.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer customerFindResponseDtoToCustomer(CustomerFindResponseDto customerFindResponseDto);
    CustomerCreateResponseDto customerToCustomerCreateResponseDto(Customer customer);

    CustomerRegisterDto customerCreateRequestDtoToCustomerRegisterDto(CustomerCreateRequestDto customerCreateRequestDto);

    Customer cutomerRegisterDtoToCustomer(CustomerRegisterDto customerRegisterDto);

    CustomerRegisterDto customerToCustomerRegisterDto(Customer customer);

    CustomerFindResponseDto customerToCustomerFindResponseDto(Customer customer);

    LoginDto customerToLoginDto(Customer customer);
}
