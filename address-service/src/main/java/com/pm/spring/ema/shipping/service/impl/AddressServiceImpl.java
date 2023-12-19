package com.pm.spring.ema.shipping.service.impl;

import com.pm.spring.ema.shipping.dto.mapper.AddressMapper;
import com.pm.spring.ema.shipping.dto.request.AddAddressRequestDto;
import com.pm.spring.ema.shipping.dto.response.AddAddressResponseDto;
import com.pm.spring.ema.shipping.model.dao.AddressDao;
import com.pm.spring.ema.shipping.service.AddressService;
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

        if (Boolean.TRUE.equals(addressRequestDto.getDefaultAddress())) {
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

        var address = addressMapper.convertToAddress(addressRequestDto);
        address.setUserUuid(userId);
        var response = addressMapper.convertToAddAddressResponseDto(addressDao.save(address));

        log.info("AddressService:addAddress response : {}", response);
        log.info("AddressService:addAddress Execution Ended");

        return response;
    }
}
