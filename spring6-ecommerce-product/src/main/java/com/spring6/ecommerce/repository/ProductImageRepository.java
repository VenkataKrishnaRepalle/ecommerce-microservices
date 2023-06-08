package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {

    @Query("select pi from ProductImage pi where pi.name = :fileName")
    public ProductImage isProductImageNameExists(@Param("fileName") String fileName);
}
