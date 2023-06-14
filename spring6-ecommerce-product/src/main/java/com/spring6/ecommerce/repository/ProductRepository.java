package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.List;
import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("UPDATE Product p SET p.isEnabled = :status WHERE p.id = :productId")
    @Modifying
    public void updateEnabledStatus(@Param("status") boolean status, @Param("productId") UUID productId);

    @Query("SELECT count(p) FROM Product p WHERE p.id = :productId")
    public long countById(@Param("productId") UUID productId);

    @Query("SELECT p FROM Product p WHERE p.name = :productName")
    public Product findByName(@Param("productName") String productName);

    @Query("SELECT CASE WHEN EXISTS (SELECT p FROM Product p WHERE p.mainImage = :fileName)THEN CAST(true As boolean ) ELSE CAST(false as boolean) END")
    public boolean isProductMainImageExists(@Param("fileName") String fileName);

    @Query("SELECT p FROM Product p WHERE p.name LIKE '%:keyword'")
    public List<Product> findByPage(@Param("keyword") String keyword, Pageable pageable);

}
