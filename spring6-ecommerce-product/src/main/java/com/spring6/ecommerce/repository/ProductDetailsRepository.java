package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.ProductDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface ProductDetailsRepository extends JpaRepository<ProductDetails, UUID> {

    @Query("SELECT pd FROM ProductDetails pd WHERE pd.productId = :productId AND pd.name = :detailName and pd.value = :detailValue")
    ProductDetails isProductDetailsExists(@Param("productId") UUID productId, @Param("detailName") String detailName, @Param("detailValue") String detailValue);
}
