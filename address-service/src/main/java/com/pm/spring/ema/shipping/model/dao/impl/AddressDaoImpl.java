package com.pm.spring.ema.shipping.model.dao.impl;

import com.pm.spring.ema.shipping.model.dao.AddressDao;
import com.pm.spring.ema.shipping.model.entity.Address;
import com.pm.spring.ema.shipping.model.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class AddressDaoImpl implements AddressDao {

    private final AddressRepository addressRepository;

    public Address save(Address address) {
        return addressRepository.save(address);
    }

    public List<Address> getAllByUserId(UUID userId) {
        return addressRepository.getAllByUserUuid(userId);
    }
}
