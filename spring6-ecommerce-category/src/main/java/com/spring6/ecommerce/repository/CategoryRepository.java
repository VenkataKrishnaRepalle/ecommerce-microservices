package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.Category;
import com.spring6.ecommerce.entity.ParentCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Long countById(UUID id);

    Optional<Category> findByName(String name);

    List<Category> findByParentCategory(ParentCategory parentCategory);

    @Query("SELECT c FROM Category c WHERE c.name LIKE '%:keyword%'")
    List<Category> findByPage(@Param("keyword")String keyword, Pageable pageable);

}
