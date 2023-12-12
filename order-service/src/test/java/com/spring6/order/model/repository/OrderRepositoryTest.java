package com.pm.spring.ema.order.model.repository;

import com.pm.spring.ema.order.model.entity.Order;
import com.pm.spring.ema.common.enums.BrandStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void testCreateBrand() {
        Order savedOrder = orderRepository.save(getBrand());

        assertThat(savedOrder).isNotNull();
        assertThat(savedOrder.getId()).isNotNull();

    }
    private Order getBrand() {
        return Order.builder()
                .name("Acer")
                .imageName("Acer.png")
                .status(BrandStatusEnum.ACTIVE)
                .subcategoryId(UUID.randomUUID())
                .build();
    }

}