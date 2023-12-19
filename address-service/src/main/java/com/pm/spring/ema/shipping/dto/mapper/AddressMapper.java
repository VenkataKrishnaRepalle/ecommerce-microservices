package com.pm.spring.ema.shipping.dto.mapper;

import com.pm.spring.ema.shipping.dto.request.AddAddressRequestDto;
import com.pm.spring.ema.shipping.dto.response.AddAddressResponseDto;
import com.pm.spring.ema.shipping.model.entity.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    AddAddressResponseDto convertToAddAddressResponseDto(Address address);
    Address convertToAddress(AddAddressRequestDto addressRequestDto);
}
