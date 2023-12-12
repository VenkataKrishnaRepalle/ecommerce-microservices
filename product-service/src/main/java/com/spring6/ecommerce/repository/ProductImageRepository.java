package com.pm.spring.ema.ecommerce.model.repository;

import com.pm.spring.ema.ecommerce.model.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {

    @Query("SELECT CASE WHEN EXISTS (SELECT p FROM ProductImage p WHERE p.name = :fileName)THEN CAST(true As boolean ) ELSE CAST(false as boolean) END")
    public boolean isProductImageNameExists(@Param("fileName") String fileName);
}
