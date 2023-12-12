package com.pm.spring.ema.order.bootstrap;

import com.pm.spring.ema.order.model.entity.Order;
import com.pm.spring.ema.order.model.enums.OrderStatus;
import com.pm.spring.ema.order.model.repository.OrderRepository;
import com.pm.spring.ema.common.enums.BrandStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class BootstrapData implements CommandLineRunner {
    private final OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
//        loadOrderData();
    }

    private void loadOrderData() {
        if (orderRepository.count() == 0) {
            orderRepository.save(Order.builder()
                    .firstName("Kamal")
                    .lastName("Karthik")
                    .status(OrderStatus.PROCESSING)
                    .build());
        }
    }
}
