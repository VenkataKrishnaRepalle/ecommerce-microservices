package com.pm.spring.ema.permission.service.model.dao;


import com.pm.spring.ema.permission.service.model.entity.Policy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientDao {

    List<Policy> findAll();

    Optional<Policy> findById(UUID id);

    Policy save(Policy user);

    void delete(Policy user);

    Page<Policy> findAll(Pageable pageable);

    void deleteById(UUID id);

    Optional<Policy> findByName(String name);

    Long countById(UUID id);
}
