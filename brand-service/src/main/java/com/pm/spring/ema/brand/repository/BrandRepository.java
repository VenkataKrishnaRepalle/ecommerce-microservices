package com.pm.spring.ema.brand.repository;

import com.pm.spring.ema.brand.entity.Brand;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BrandRepository extends JpaRepository<Brand, UUID> {

  Long countById(UUID id);

  Optional<Brand> findByName(String name);

  //    @Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
  Page<Brand> findAll(Pageable pageable);

  @Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
  Page<Brand> findAllByName(String searchKeyword, Pageable pageable);
}
