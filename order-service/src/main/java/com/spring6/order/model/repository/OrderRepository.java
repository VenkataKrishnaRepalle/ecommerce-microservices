package com.spring6.order.model.repository;

import com.spring6.order.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    Long countById(UUID id);

    List<Order> getAllByUserId(UUID userId);
}
