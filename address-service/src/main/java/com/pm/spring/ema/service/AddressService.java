package com.pm.spring.ema.service;

import com.pm.spring.ema.dto.request.AddAddressRequestDto;
import com.pm.spring.ema.dto.request.UpdateAddressRequestDto;
import com.pm.spring.ema.dto.response.AddAddressResponseDto;
import com.pm.spring.ema.dto.response.AddressFindResponseDto;
import com.pm.spring.ema.dto.response.UpdateAddressResponseDto;

import java.util.UUID;

public interface AddressService {
    AddAddressResponseDto addAddress(UUID userId, AddAddressRequestDto addressRequestDto);

    AddressFindResponseDto getAddressById(UUID addressId);

    UpdateAddressResponseDto updateAddressById(UUID addressId, UpdateAddressRequestDto updateAddressRequestDto);

    void updateDefaultAddress(UUID addressId);

    void deleteAddress(UUID addressId);
}
