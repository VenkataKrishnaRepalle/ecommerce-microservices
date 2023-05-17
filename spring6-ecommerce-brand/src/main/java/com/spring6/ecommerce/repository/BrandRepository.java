package com.spring6.ecommerce.repository;


import com.spring6.ecommerce.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {

}
