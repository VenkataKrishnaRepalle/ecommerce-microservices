package com.pm.spring.ema.group.service.model.dao.impl;

import com.pm.spring.ema.group.service.model.dao.ClientDao;
import com.pm.spring.ema.group.service.model.entity.Client;
import com.pm.spring.ema.group.service.model.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class ClientDaoImpl implements ClientDao {

    private final ClientRepository clientRepository;

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Client> findById(UUID id) {
        return clientRepository.findById(id);
    }

    @Override
    public Client save(Client user) {
        return clientRepository.save(user);
    }
    @Override
    public void delete(Client user) {
        clientRepository.delete(user);
    }

    @Override
    public Page<Client> findAll(Pageable pageable) {
        return clientRepository.findAll(pageable);
    }

    @Override
    public void deleteById(UUID id) {
        clientRepository.deleteById(id);
    }

    @Override
    public Long countById(UUID id) {
        return clientRepository.countById(id);
    }

    @Override
    public Optional<Client> findByName(String name) {
        return clientRepository.findByName(name);
    }
}
