package com.pm.spring.ema.model.dao.Impl;

import com.pm.spring.ema.model.dao.ProductDao;
import com.pm.spring.ema.model.entity.Product;
import com.pm.spring.ema.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class ProductDaoImpl implements ProductDao {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return productRepository.findById(id);
    }

    @Override
    public Product save(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void updateEnabledStatus(Boolean status, UUID id) {
        productRepository.updateEnabledStatus(status, id);
    }

    @Override
    public Long countById(UUID id) {
        return productRepository.countById(id);
    }

    @Override
    public void deleteById(UUID id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product findByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getByCategoryId(UUID id) {
        return productRepository.getByCategoryId(id);
    }

    @Override
    public List<Product> getByBrandId(UUID id) {
        return productRepository.getByCategoryId(id);
    }

    @Override
    public List<Product> getByCategoryIdAndBrandId(UUID categoryId, UUID brandId) {
        return productRepository.getByCategoryIdAndBrandId(categoryId, brandId);
    }

    @Override
    public Page<Product> findAll(String keyword, Pageable pageable) {
        return productRepository.findAll(keyword, pageable);
    }
}
