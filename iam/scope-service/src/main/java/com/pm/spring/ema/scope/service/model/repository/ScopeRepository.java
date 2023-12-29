package com.pm.spring.ema.client.service.model.repository;

import com.pm.spring.ema.client.service.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {
    Optional<Client> findById(UUID clientId);

    Optional<Client> findByName(String name);

    Long countById(UUID id);

}