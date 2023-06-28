package com.spring6.mapper;

import com.spring6.dto.CustomerCreateRequestDto;
import com.spring6.dto.CustomerCreateResponseDto;
import com.spring6.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    Customer customerCreateRequestDtoToCustomer(CustomerCreateRequestDto customerCreateRequestDto);

    CustomerCreateRequestDto customerToCustomerCreateRequestdto(Customer customer);

    CustomerCreateResponseDto customerToCustomerCreateResponseDto(Customer customer);
}
