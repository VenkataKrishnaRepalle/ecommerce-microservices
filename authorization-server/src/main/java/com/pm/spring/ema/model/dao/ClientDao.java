package com.pm.spring.ema.model.dao;


import com.pm.spring.ema.model.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClientDao {

    List<Client> findAll();

    Optional<Client> findById(UUID id);

    Client save(Client user);

    void delete(Client user);

    Page<Client> findAll(Pageable pageable);

    void deleteById(UUID id);

    Optional<Client> findByName(String name);

    Long countById(UUID id);
}
