package com.pm.spring.ema.policy.service.model.dao.impl;

import com.pm.spring.ema.policy.service.model.dao.ClientDao;
import com.pm.spring.ema.policy.service.model.entity.Policy;
import com.pm.spring.ema.policy.service.model.repository.ClientRepository;
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
    public List<Policy> findAll() {
        return clientRepository.findAll();
    }

    @Override
    public Optional<Policy> findById(UUID id) {
        return clientRepository.findById(id);
    }

    @Override
    public Policy save(Policy user) {
        return clientRepository.save(user);
    }
    @Override
    public void delete(Policy user) {
        clientRepository.delete(user);
    }

    @Override
    public Page<Policy> findAll(Pageable pageable) {
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
    public Optional<Policy> findByName(String name) {
        return clientRepository.findByName(name);
    }
}
