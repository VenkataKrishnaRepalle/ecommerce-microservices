package com.pm.spring.ema.shipping.model.dao.impl;

import com.pm.spring.ema.shipping.model.dao.AddressDao;
import com.pm.spring.ema.shipping.model.entity.Address;
import com.pm.spring.ema.shipping.model.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class AddressDaoImpl implements AddressDao {

    private final AddressRepository addressRepository;

    @Override
    public Address save(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public List<Address> getAllByUserId(UUID userId) {
        return addressRepository.getAllByUserUuid(userId);
    }

    @Override
    public Integer countAddressesByUserId(UUID userId) {
        return addressRepository.countAllByUserUuid(userId);
    }

    @Override
    public Optional<Address> findById(UUID userId) {
        return addressRepository.findById(userId);
    }

    @Override
    public Integer countAllByUserUuidAndDefaultAddress(UUID userId, Boolean defaultAddress) {
        return addressRepository.countAllByUserUuidAndDefaultAddress(userId, defaultAddress);
    }

    @Override
    public void deleteById(UUID addressId) {
        addressRepository.deleteById(addressId);
    }

    @Override
    public List<Address> getAllAddressesByUserId(UUID userId) {
        return addressRepository.getAllByUserUuid(userId);
    }
}
