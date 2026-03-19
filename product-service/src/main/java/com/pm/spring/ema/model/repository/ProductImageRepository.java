package com.pm.spring.ema.model.repository;

import com.pm.spring.ema.model.entity.ProductImage;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductImageRepository extends JpaRepository<ProductImage, UUID> {

  @Query(
      "SELECT CASE WHEN EXISTS (SELECT p FROM ProductImage p WHERE p.name = :fileName)THEN CAST(true As boolean ) ELSE CAST(false as boolean) END")
  public boolean isProductImageNameExists(@Param("fileName") String fileName);
}
