package com.pm.spring.ema.model.repository;

import com.pm.spring.ema.model.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

    List<Address> getAllByUserUuid(UUID userId);

    Integer countAllByUserUuid(UUID userId);

    Integer countAllByUserUuidAndDefaultAddress(UUID userId, Boolean defaultAddress);
}
