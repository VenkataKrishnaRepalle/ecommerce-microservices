package com.spring6.order.model.repository;


import com.spring6.order.model.entity.Order;

import java.util.UUID;


@Repository
public interface OrderRepository extends ReactiveCrudRepository<Order, UUID> {
//
//    Long countById(UUID id);
//
//    Optional<Order> findByName(String name);
//
////    @Query("SELECT b FROM Brand b WHERE b.name LIKE %?1%")
//    Page<Order> findAll(Pageable pageable);
//
//    @Query("SELECT b FROM Order b WHERE b.name LIKE %?1%")
//    Page<Order> findAllByName(String searchKeyword, Pageable pageable);
}
