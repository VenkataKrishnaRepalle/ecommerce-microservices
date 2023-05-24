package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.PatentCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ParentCategoryRepository extends JpaRepository<PatentCategory, UUID> {

}
