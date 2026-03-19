package com.pm.spring.ema.brand.model.dao;

import com.pm.spring.ema.brand.model.entity.Brand;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BrandDao {

  List<Brand> findAll();

  Optional<Brand> findById(UUID id);

  Brand save(Brand user);

  void delete(Brand user);

  Page<Brand> findAllByName(String searchKeyword, Pageable pageable);

  Page<Brand> findAll(Pageable pageable);

  Long countById(UUID id);

  void deleteById(UUID id);

  Optional<Brand> findByName(String name);
}
