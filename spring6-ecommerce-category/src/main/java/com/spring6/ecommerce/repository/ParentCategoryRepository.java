package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.ParentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParentCategoryRepository extends JpaRepository<ParentCategory, UUID> {

    Long countById(UUID id);
}
