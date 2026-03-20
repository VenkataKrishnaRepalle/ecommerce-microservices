package com.pm.spring.ema.service.impl;

import com.pm.spring.ema.common.util.exception.InvalidInputException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.dto.AddressDto;
import com.pm.spring.ema.exception.FoundException;
import com.pm.spring.ema.exception.LimitException;
import com.pm.spring.ema.exception.NotFoundException;
import com.pm.spring.ema.exception.UpdateException;
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
    log.info("AddressService:addAddress Execution Started");
    if (addressRequestDto == null || addressRequestDto.getUserUuid() == null) {
      throw new InvalidInputException("Add address", "Invalid address");
    }
    var userId = addressRequestDto.getUserUuid();

    if (5 <= addressRepository.countAllByUserUuid(userId)) {
      log.error("dressService:addAddress errorMessage : {}", ErrorCodes.E5012);
      log.info("AddressService:addAddress Execution Ended");
      throw new LimitException(ErrorCodes.E5012);
    }

    if (addressRequestDto.getDefaultAddress()) {
      setDefaultAddressFalse(userId);
    }

    var address = addressMapper.convertToAddress(addressRequestDto);
    var response = addressMapper.convertToAddressDto(addressRepository.save(address));

    log.info("AddressService:addAddress response : {}", response);
    log.info("AddressService:addAddress Execution Ended");

    return response;
  }

  @Override
  public AddressDto getAddressById(UUID addressId) {
    log.info("AddressService:getAddressById Execution Started");

    var address = addressRepository.findById(addressId);

    if (address.isEmpty()) {
      log.error("AddressService:getAddressById errorMessage : {}", ErrorCodes.E5013 + addressId);
      log.info("AddressService:getAddressById Execution Ended");
      throw new NotFoundException(ErrorCodes.E5013 + addressId);
    }

    log.info("AddressService:getAddressById response : {}", address.get());
    log.info("AddressService:getAddressById Execution Ended");
    return addressMapper.convertToAddressDto(address.get());
  }

  @Override
  public AddressDto updateAddressById(UUID addressId, AddressDto updateAddressRequestDto) {
    log.info("AddressService:updateAddressById Execution Started");

    var address = addressRepository.findById(addressId);

    if (address.isEmpty()) {
      log.error("AddressService:updateAddressById errorMessage : {}", ErrorCodes.E5013 + addressId);
      log.info("AddressService:updateAddressById Execution Ended");
      throw new NotFoundException(ErrorCodes.E5013 + addressId);
    }
    var updateAddress = address.get();

    updateAddress.setCountry(updateAddressRequestDto.getCountry());
    updateAddress.setFullName(updateAddressRequestDto.getFullName());
    updateAddress.setMobileNumber(updateAddressRequestDto.getMobileNumber());
    updateAddress.setPincode(updateAddressRequestDto.getPincode());
    updateAddress.setHouseNumber(updateAddressRequestDto.getHouseNumber());
    updateAddress.setArea(updateAddressRequestDto.getArea());
    updateAddress.setLandmark(updateAddressRequestDto.getLandmark());
    updateAddress.setCity(updateAddressRequestDto.getCity());
    updateAddress.setState(updateAddressRequestDto.getState());

    if (updateAddressRequestDto.getDefaultAddress()) {
      updateAddress.setDefaultAddress(true);
      setDefaultAddressFalse(updateAddress.getUserUuid());
    } else {
      var count =
          addressRepository.countAllByUserUuidAndDefaultAddress(
              updateAddress.getUserUuid(), Boolean.TRUE);
      if (count == 0) {
        updateAddress.setDefaultAddress(Boolean.TRUE);
      }
    }

    var updatedAddress = addressRepository.save(updateAddress);
    log.info("AddressService:updateAddressById response : {}", updatedAddress);
    log.info("AddressService:updateAddressById Execution Ended");
    return addressMapper.convertToAddressDto(updatedAddress);
  }

  @Override
  public void updateDefaultAddress(UUID addressId) {
    log.info("AddressService:updateDefaultAddress Execution Started");

    var address = addressRepository.findById(addressId);

    if (address.isEmpty()) {
      log.error(
          "AddressService:updateDefaultAddress errorMessage: {}", ErrorCodes.E5013 + addressId);
      log.info("AddressService:updateDefaultAddress Execution Ended");
      throw new NotFoundException(ErrorCodes.E5013 + addressId);
    }

    var updateAddress = address.get();

    setDefaultAddressFalse(updateAddress.getUserUuid());

    updateAddress.setDefaultAddress(Boolean.TRUE);

    var updatedAddress = addressRepository.save(updateAddress);

    if (Boolean.FALSE.equals(updatedAddress.getDefaultAddress())) {
      log.error(
          "AddressService:updateDefaultAddress errorMessage: {}", ErrorCodes.E5014 + addressId);
      log.info("AddressService:updateDefaultAddress Execution Ended");
      throw new UpdateException(ErrorCodes.E5014 + addressId);
    }

    log.info("AddressService:updateDefaultAddress Execution Ended");
  }

  @Override
  public void deleteAddress(UUID addressId) {
    log.info("AddressService:deleteAddress Execution Started");

    var address = addressRepository.findById(addressId);

    if (address.isEmpty()) {
      log.error("AddressService:deleteAddress errorMessage: {}", ErrorCodes.E5013 + addressId);
      log.info("AddressService:deleteAddress Execution Ended");
      throw new NotFoundException(ErrorCodes.E5013 + addressId);
    }

    addressRepository.deleteById(addressId);

    var deletedAddress = addressRepository.findById(addressId);

    if (deletedAddress.isPresent()) {
      log.error("AddressService:deleteAddress errorMessage: {}", ErrorCodes.E5015 + addressId);
      log.info("AddressService:deleteAddress Execution Ended");
      throw new FoundException(ErrorCodes.E5015 + addressId);
    }

    var addresses = addressRepository.getAllByUserUuid(address.get().getUserUuid());

    if (addresses != null && !addresses.isEmpty()) {
      var updateAddress = addresses.get(0);
      updateDefaultAddress(updateAddress.getId());
    }

    log.info("AddressService:deleteAddress Execution Ended");
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
