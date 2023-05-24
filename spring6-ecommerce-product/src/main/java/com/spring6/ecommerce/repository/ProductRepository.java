package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    Long countById(UUID id);
}
