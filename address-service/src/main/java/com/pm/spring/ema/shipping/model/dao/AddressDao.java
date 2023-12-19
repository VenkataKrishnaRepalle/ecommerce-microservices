package com.pm.spring.ema.shipping.model.dao;

import com.pm.spring.ema.shipping.model.entity.Address;

import java.util.List;
import java.util.UUID;

public interface AddressDao {
    Address save(Address address);

    List<Address> getAllByUserId(UUID userId);
}
