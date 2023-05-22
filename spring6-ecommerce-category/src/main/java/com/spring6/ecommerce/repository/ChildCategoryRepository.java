package com.spring6.ecommerce.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ChildCategoryRepository extends JpaRepository<CategoryRepository, UUID> {
}
