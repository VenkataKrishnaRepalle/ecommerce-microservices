package com.pm.spring.ema.product.repository;

import com.pm.spring.ema.product.entity.ProductDetails;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, UUID> {

  @Query(
      "SELECT pd FROM ProductDetails pd WHERE pd.productId = :productId AND pd.name = :detailName")
  ProductDetails isProductDetailsExists(
      @Param("productId") UUID productId, @Param("detailName") String detailName);

  @Query(
      "SELECT CASE WHEN EXISTS (SELECT pd FROM ProductDetails pd WHERE pd.productId = :productId)THEN CAST(true As boolean ) ELSE CAST(false as boolean) END")
  boolean isProductDetailsExistsWithProductId(@Param("productId") UUID productId);

  @Query("SELECT pd FROM ProductDetails pd WHERE pd.productId = :id")
  List<ProductDetails> getByProductId(@Param("id") UUID id);
}
