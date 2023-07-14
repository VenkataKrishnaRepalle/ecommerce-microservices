package com.spring6.order.model.repository;

import com.spring6.order.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Long countById(UUID id);

    //    @Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
    Page<Order> findAll(Pageable pageable);

}
