package com.spring6.order.model.dao;


import com.spring6.order.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderDao {

    List<Order> findAll();

    Optional<Order> findById(UUID id);

    Order save(Order user);

    Page<Order> findAll(Pageable pageable);

    Long countById(UUID id);

    void deleteById(UUID id);
}
