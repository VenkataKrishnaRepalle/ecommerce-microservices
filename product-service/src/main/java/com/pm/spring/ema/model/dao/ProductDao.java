package com.pm.spring.ema.model.dao;

import com.pm.spring.ema.model.entity.Product;
import io.micrometer.common.KeyValues;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductDao {
    List<Product> findAll();

    Optional<Product> findById(UUID id);

    Product save(Product product);

    void updateEnabledStatus(Boolean status, UUID id);

    Long countById(UUID id);

    void deleteById(UUID id);

    Product findByName(String name);

    List<Product> getByCategoryId(UUID id);
    
    List<Product> getByBrandId(UUID id);

    List<Product> getByCategoryIdAndBrandId(UUID categoryId, UUID brandId);

    Page<Product> findAll(String keyword, Pageable pageable);
}
