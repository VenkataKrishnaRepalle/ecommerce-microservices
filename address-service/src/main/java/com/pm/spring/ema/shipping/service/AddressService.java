package com.pm.spring.ema.shipping.service;

import com.pm.spring.ema.shipping.dto.request.AddAddressRequestDto;
import com.pm.spring.ema.shipping.dto.response.AddAddressResponseDto;

import java.util.UUID;

public interface AddressService {
    AddAddressResponseDto addAddress(UUID userId, AddAddressRequestDto addressRequestDto);
}
