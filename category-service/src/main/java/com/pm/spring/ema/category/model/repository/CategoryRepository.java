package com.pm.spring.ema.category.model.repository;

import com.pm.spring.ema.category.model.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Optional<Category> findByName(String name);

    @Query("SELECT c FROM Category c WHERE c.name LIKE '%:keyword%'")
    Page<Category> findAllByPage(String keyword, Pageable pageable);
}
