package com.pm.spring.ema.ecommerce.repository;

import com.pm.spring.ema.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {


    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name LIKE '%:keyword%'")
    List<Category> findByPage(String keyword, Pageable pageable);
}
