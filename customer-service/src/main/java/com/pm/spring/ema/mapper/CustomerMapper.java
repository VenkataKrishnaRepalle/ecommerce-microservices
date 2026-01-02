package com.pm.spring.ema.mapper;

import com.pm.spring.ema.dto.*;
import com.pm.spring.ema.modal.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    Customer cutomerRegisterDtoToCustomer(CustomerDto customerDto);

    CustomerDto customerToCustomerDto(Customer customer);
}
