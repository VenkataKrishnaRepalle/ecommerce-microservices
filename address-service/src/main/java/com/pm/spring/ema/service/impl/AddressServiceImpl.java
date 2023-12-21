package com.pm.spring.ema.service.impl;

import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.exception.FoundException;
import com.pm.spring.ema.exception.LimitException;
import com.pm.spring.ema.exception.NotFoundException;
import com.pm.spring.ema.exception.UpdateException;
import com.pm.spring.ema.dto.mapper.AddressMapper;
import com.pm.spring.ema.dto.request.AddAddressRequestDto;
import com.pm.spring.ema.dto.request.UpdateAddressRequestDto;
import com.pm.spring.ema.dto.response.AddAddressResponseDto;
import com.pm.spring.ema.dto.response.AddressFindResponseDto;
import com.pm.spring.ema.dto.response.UpdateAddressResponseDto;
import com.pm.spring.ema.model.dao.AddressDao;
import com.pm.spring.ema.service.AddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressDao addressDao;

    private final AddressMapper addressMapper;

    @Override
    public AddAddressResponseDto addAddress(UUID userId, AddAddressRequestDto addressRequestDto) {
        log.info("AddressService:addAddress Execution Started");

        if (5 <= addressDao.countAddressesByUserId(userId)) {
            log.error("dressService:addAddress errorMessage : {}", ErrorCodes.E5012);
            log.info("AddressService:addAddress Execution Ended");
            throw new LimitException(ErrorCodes.E5012);
        }

        if (Boolean.TRUE.equals(addressRequestDto.getDefaultAddress())) {
            setDefaultAddressFalse(userId);
        }

        var address = addressMapper.convertToAddress(addressRequestDto);
        address.setUserUuid(userId);
        var response = addressMapper.convertToAddAddressResponseDto(addressDao.save(address));

        log.info("AddressService:addAddress response : {}", response);
        log.info("AddressService:addAddress Execution Ended");

        return response;
    }

    @Override
    public AddressFindResponseDto getAddressById(UUID addressId) {
        log.info("AddressService:getAddressById Execution Started");

        var address = addressDao.findById(addressId);

        if (address.isEmpty()) {
            log.error("AddressService:getAddressById errorMessage : {}", ErrorCodes.E5013 + addressId.toString());
            log.info("AddressService:getAddressById Execution Ended");
            throw new NotFoundException(ErrorCodes.E5013 + addressId);
        }

        log.info("AddressService:getAddressById response : {}", address.get());
        log.info("AddressService:getAddressById Execution Ended");
        return addressMapper.convertToAddressFindResponseDto(address.get());
    }

    @Override
    public UpdateAddressResponseDto updateAddressById(UUID addressId, UpdateAddressRequestDto updateAddressRequestDto) {
        log.info("AddressService:updateAddressById Execution Started");

        var address = addressDao.findById(addressId);

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

        if (Boolean.TRUE.equals(updateAddressRequestDto.getDefaultAddress())) {
            updateAddress.setDefaultAddress(updateAddressRequestDto.getDefaultAddress());
            setDefaultAddressFalse(updateAddress.getUserUuid());
        } else {
            var count = addressDao.countAllByUserUuidAndDefaultAddress(updateAddress.getUserUuid(), Boolean.TRUE);
            if (count == 0) {
                updateAddress.setDefaultAddress(Boolean.TRUE);
            }
        }

        var updatedAddress = addressDao.save(updateAddress);
        log.info("AddressService:updateAddressById response : {}", updatedAddress);
        log.info("AddressService:updateAddressById Execution Ended");
        return addressMapper.convertToUpdateAddressResponseDto(updatedAddress);
    }

    @Override
    public void updateDefaultAddress(UUID addressId) {
        log.info("AddressService:updateDefaultAddress Execution Started");

        var address = addressDao.findById(addressId);

        if (address.isEmpty()) {
            log.error("AddressService:updateDefaultAddress errorMessage: {}", ErrorCodes.E5013 + addressId);
            log.info("AddressService:updateDefaultAddress Execution Ended");
            throw new NotFoundException(ErrorCodes.E5013 + addressId);
        }

        var updateAddress = address.get();

        setDefaultAddressFalse(updateAddress.getUserUuid());

        updateAddress.setDefaultAddress(Boolean.TRUE);

        var updatedAddress = addressDao.save(updateAddress);

        if (Boolean.FALSE.equals(updatedAddress.getDefaultAddress())) {
            log.error("AddressService:updateDefaultAddress errorMessage: {}", ErrorCodes.E5014 + addressId);
            log.info("AddressService:updateDefaultAddress Execution Ended");
            throw new UpdateException(ErrorCodes.E5014 + addressId);
        }

        log.info("AddressService:updateDefaultAddress Execution Ended");
    }

    @Override
    public void deleteAddress(UUID addressId) {
        log.info("AddressService:deleteAddress Execution Started");

        var address = addressDao.findById(addressId);

        if (address.isEmpty()) {
            log.error("AddressService:deleteAddress errorMessage: {}", ErrorCodes.E5013 + addressId);
            log.info("AddressService:deleteAddress Execution Ended");
            throw new NotFoundException(ErrorCodes.E5013 + addressId);
        }

        addressDao.deleteById(addressId);

        var deletedAddress = addressDao.findById(addressId);

        if (deletedAddress.isPresent()) {
            log.error("AddressService:deleteAddress errorMessage: {}", ErrorCodes.E5015 + addressId);
            log.info("AddressService:deleteAddress Execution Ended");
            throw new FoundException(ErrorCodes.E5015 + addressId);
        }

        var addresses = addressDao.getAllAddressesByUserId(address.get().getUserUuid());

        if(addresses != null && !addresses.isEmpty()) {
            var updateAddress = addresses.get(0);
            updateDefaultAddress(updateAddress.getId());
        }

        log.info("AddressService:deleteAddress Execution Ended");
    }

    private void setDefaultAddressFalse(UUID userId) {
        var addressList = addressDao.getAllByUserId(userId);
        if (addressList != null) {
            for (var address : addressList) {
                if (Boolean.TRUE.equals(address.getDefaultAddress())) {
                    address.setDefaultAddress(Boolean.FALSE);
                    addressDao.save(address);
                }
            }
        }
    }
}
