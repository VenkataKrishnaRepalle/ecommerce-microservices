package com.pm.spring.ema.repository;

import com.pm.spring.ema.modal.Address;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {

  List<Address> getAllByUserUuid(UUID userId);

  Integer countAllByUserUuid(UUID userId);

  Integer countAllByUserUuidAndDefaultAddress(UUID userId, Boolean defaultAddress);
}
