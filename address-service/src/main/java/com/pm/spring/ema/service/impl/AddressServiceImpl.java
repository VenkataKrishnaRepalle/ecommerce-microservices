package com.pm.spring.ema.service.impl;

import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.InvalidInputException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.dto.AddressDto;
import com.pm.spring.ema.mapper.AddressMapper;
import com.pm.spring.ema.repository.AddressRepository;
import com.pm.spring.ema.service.AddressService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

  private final AddressRepository addressRepository;

  private final AddressMapper addressMapper;

  @Override
  public AddressDto addAddress(AddressDto addressRequestDto) {
    if (addressRequestDto == null || addressRequestDto.getUserUuid() == null) {
      throw new InvalidInputException("Add address", "Invalid address");
    }
    var userId = addressRequestDto.getUserUuid();

    if (5 <= addressRepository.countAllByUserUuid(userId)) {
      throw new InvalidInputException("", ErrorCodes.E5012);
    }
    if (addressRequestDto.getDefaultAddress()) {
      setDefaultAddressFalse(userId);
    }
    var address = addressMapper.convertToAddress(addressRequestDto);
    return addressMapper.convertToAddressDto(addressRepository.save(address));
  }

  @Override
  public AddressDto getAddressById(UUID addressId) {
    var address =
        addressRepository
            .findById(addressId)
            .orElseThrow(() -> new NotFoundException(ErrorCodes.E5013, addressId.toString()));
    return addressMapper.convertToAddressDto(address);
  }

  @Override
  public AddressDto updateAddressById(UUID addressId, AddressDto updateAddressRequestDto) {
    var address =
        addressRepository
            .findById(addressId)
            .orElseThrow(() -> new NotFoundException(ErrorCodes.E5013, addressId.toString()));
    address.setCountry(updateAddressRequestDto.getCountry());
    address.setFullName(updateAddressRequestDto.getFullName());
    address.setMobileNumber(updateAddressRequestDto.getMobileNumber());
    address.setPincode(updateAddressRequestDto.getPincode());
    address.setHouseNumber(updateAddressRequestDto.getHouseNumber());
    address.setArea(updateAddressRequestDto.getArea());
    address.setLandmark(updateAddressRequestDto.getLandmark());
    address.setCity(updateAddressRequestDto.getCity());
    address.setState(updateAddressRequestDto.getState());

    if (updateAddressRequestDto.getDefaultAddress()) {
      address.setDefaultAddress(true);
      setDefaultAddressFalse(address.getUserUuid());
    } else {
      var count =
          addressRepository.countAllByUserUuidAndDefaultAddress(
              address.getUserUuid(), Boolean.TRUE);
      if (count == 0) {
        address.setDefaultAddress(Boolean.TRUE);
      }
    }

    var updatedAddress = addressRepository.save(address);
    return addressMapper.convertToAddressDto(updatedAddress);
  }

  @Override
  public void updateDefaultAddress(UUID addressId) {
    var address =
        addressRepository
            .findById(addressId)
            .orElseThrow(() -> new NotFoundException(ErrorCodes.E5013, addressId.toString()));
    setDefaultAddressFalse(address.getUserUuid());
    address.setDefaultAddress(Boolean.TRUE);

    var updatedAddress = addressRepository.save(address);
    if (Boolean.FALSE.equals(updatedAddress.getDefaultAddress())) {
      throw new InvalidInputException(ErrorCodes.E5014, addressId.toString());
    }
  }

  @Override
  public void deleteAddress(UUID addressId) {
    var address =
        addressRepository
            .findById(addressId)
            .orElseThrow(() -> new NotFoundException(ErrorCodes.E5013, addressId.toString()));
    addressRepository.deleteById(addressId);
    var deletedAddress = addressRepository.findById(addressId);
    if (deletedAddress.isPresent()) {
      throw new FoundException(ErrorCodes.E5015, addressId.toString());
    }
    var addresses = addressRepository.getAllByUserUuid(address.getUserUuid());
    if (addresses != null && !addresses.isEmpty()) {
      var updateAddress = addresses.get(0);
      updateDefaultAddress(updateAddress.getId());
    }
  }

  private void setDefaultAddressFalse(UUID userId) {
    var addressList = addressRepository.getAllByUserUuid(userId);
    if (addressList != null) {
      for (var address : addressList) {
        if (Boolean.TRUE.equals(address.getDefaultAddress())) {
          address.setDefaultAddress(Boolean.FALSE);
          addressRepository.save(address);
        }
      }
    }
  }
}
