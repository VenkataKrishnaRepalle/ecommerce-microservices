package com.pm.spring.ema.order.model.dao;

import com.pm.spring.ema.order.model.entity.Order;
import com.pm.spring.ema.order.model.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Repository
public class OrderDaoImpl implements OrderDao {

    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }

    @Override
    public Optional<Order> findById(UUID id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order save(Order user) {
        return orderRepository.save(user);
    }

    @Override
    public Page<Order> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Long countById(UUID id) {
        return orderRepository.countById(id);
    }

    @Override
    public void deleteById(UUID id) {
        orderRepository.deleteById(id);
    }

}
