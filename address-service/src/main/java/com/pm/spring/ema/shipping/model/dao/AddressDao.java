package com.pm.spring.ema.shipping.model.dao;

import com.pm.spring.ema.shipping.model.entity.Address;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AddressDao {
    Address save(Address address);

    List<Address> getAllByUserId(UUID userId);

    Integer countAddressesByUserId(UUID userId);

    Optional<Address> findById(UUID userId);

    Integer countAllByUserUuidAndDefaultAddress(UUID userId, Boolean defaultAddress);

    void deleteById(UUID addressId);

    List<Address> getAllAddressesByUserId(UUID userId);
}
