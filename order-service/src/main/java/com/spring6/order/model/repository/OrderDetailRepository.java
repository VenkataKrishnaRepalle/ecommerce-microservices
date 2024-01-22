package com.spring6.order.model.repository;

import com.spring6.order.model.entity.Order;
import com.spring6.order.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, UUID> {
    List<OrderDetail> getAllByOrder_Id(UUID orderId);
}
