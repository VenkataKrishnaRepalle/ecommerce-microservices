package com.pm.spring.ema.dto.mapper;

import com.pm.spring.ema.dto.request.AddAddressRequestDto;
import com.pm.spring.ema.dto.response.AddAddressResponseDto;
import com.pm.spring.ema.dto.response.AddressFindResponseDto;
import com.pm.spring.ema.dto.response.UpdateAddressResponseDto;
import com.pm.spring.ema.model.entity.Address;
import org.mapstruct.Mapper;

@Mapper
public interface AddressMapper {
    AddAddressResponseDto convertToAddAddressResponseDto(Address address);

    Address convertToAddress(AddAddressRequestDto addressRequestDto);

    AddressFindResponseDto convertToAddressFindResponseDto(Address address);

    UpdateAddressResponseDto convertToUpdateAddressResponseDto(Address address);
}
