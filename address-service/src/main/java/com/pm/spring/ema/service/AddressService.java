package com.pm.spring.ema.service;

import com.pm.spring.ema.dto.AddressDto;
import java.util.UUID;

public interface AddressService {
  AddressDto addAddress(AddressDto addressRequestDto);

  AddressDto getAddressById(UUID addressId);

  AddressDto updateAddressById(UUID addressId, AddressDto updateAddressRequestDto);

  void updateDefaultAddress(UUID addressId);

  void deleteAddress(UUID addressId);
}
