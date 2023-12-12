package com.pm.spring.ema.ecommerce.repository;

import com.pm.spring.ema.ecommerce.entity.SubCategory;
import com.pm.spring.ema.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
    Long countById(UUID id);

    Optional<SubCategory> findByName(String name);

    List<SubCategory> findByCategory(Category category);

    @Query("SELECT c FROM SubCategory c WHERE c.name LIKE '%:keyword%'")
    List<SubCategory> findByPage(@Param("keyword") String keyword, Pageable pageable);

}
