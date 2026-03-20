package com.pm.spring.ema.product.model.repository;

import com.pm.spring.ema.product.model.entity.Product;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

  @Query("UPDATE Product p SET p.isEnabled = :status WHERE p.id = :productId")
  @Modifying
  void updateEnabledStatus(@Param("status") boolean status, @Param("productId") UUID productId);

  @Query("SELECT count(p) FROM Product p WHERE p.id = :productId")
  long countById(@Param("productId") UUID productId);

  @Query("SELECT p FROM Product p WHERE p.name = :productName")
  Product findByName(@Param("productName") String productName);

  @Query(
      "SELECT CASE WHEN EXISTS (SELECT p FROM Product p WHERE p.mainImage = :fileName)THEN CAST(true As boolean ) ELSE CAST(false as boolean) END")
  boolean isProductMainImageExists(@Param("fileName") String fileName);

  @Query("SELECT p FROM Product p WHERE p.name LIKE '%:keyword'")
  Page<Product> findAll(@Param("keyword") String keyword, Pageable pageable);

  @Query("SELECT p FROM Product p WHERE p.categoryId = :id ORDER BY p.categoryId")
  List<Product> getByCategoryId(@Param("id") UUID id);

  @Query("SELECT p FROM Product p WHERE p.brandId = :id ORDER BY p.brandId")
  List<Product> getByBrandId(@Param("id") UUID id);

  @Query(
      "SELECT p FROM Product p WHERE p.categoryId = :categoryId AND p.brandId = :brandId ORDER BY p.categoryId, p.brandId")
  List<Product> getByCategoryIdAndBrandId(
      @Param("categoryId") UUID categoryId, @Param("brandId") UUID brandId);
}
