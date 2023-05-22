package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.SubCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SubCategoryRepository extends JpaRepository<SubCategory, UUID> {
}
