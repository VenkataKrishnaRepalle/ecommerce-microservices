package com.pm.spring.ema.brand.model.dao;

import com.pm.spring.ema.brand.model.entity.Brand;
import com.pm.spring.ema.brand.model.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class BrandDaoImpl implements BrandDao {

    private final BrandRepository brandRepository;

    @Override
    public List<Brand> findAll() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> findById(UUID id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand save(Brand user) {
        return brandRepository.save(user);
    }

    @Override
    public void delete(Brand user) {
        brandRepository.delete(user);
    }

    @Override
    public Page<Brand> findAllByName(String searchKeyword, Pageable pageable) {
        return brandRepository.findAllByName(searchKeyword, pageable);
    }

    @Override
    public Page<Brand> findAll(Pageable pageable) {
        return brandRepository.findAll(pageable);
    }

    @Override
    public Long countById(UUID id) {
        return brandRepository.countById(id);
    }

    @Override
    public void deleteById(UUID id) {
        brandRepository.deleteById(id);
    }

    @Override
    public Optional<Brand> findByName(String name) {
        return brandRepository.findByName(name);
    }
}
