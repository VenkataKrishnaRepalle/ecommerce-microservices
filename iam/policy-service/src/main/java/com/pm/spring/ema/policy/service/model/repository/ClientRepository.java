package com.pm.spring.ema.policy.service.model.repository;

import com.pm.spring.ema.policy.service.model.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Policy, UUID> {
    Optional<Policy> findById(UUID clientId);

    Optional<Policy> findByName(String name);

    Long countById(UUID id);

}