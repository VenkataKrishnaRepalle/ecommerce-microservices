package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;
@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    @Query("update Product p set p.isEnabled = :status where p.id = :productId")
    @Modifying
    public void updateEnabledStatus(@Param("status") boolean status, @Param("productId") UUID productId);

    @Query("select count(p) from Product p where p.id = :productId")
    public long countById(@Param("productId") UUID productId);

}
