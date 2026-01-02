package com.pm.spring.ema.mapper;

import com.pm.spring.ema.dto.AddressDto;
import com.pm.spring.ema.modal.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {

    Address convertToAddress(AddressDto addressRequestDto);

    AddressDto convertToAddressDto(Address address);
}
