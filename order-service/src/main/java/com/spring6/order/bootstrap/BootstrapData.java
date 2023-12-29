package com.spring6.order.bootstrap;


import com.spring6.order.model.entity.Order;
import com.spring6.order.model.enums.OrderStatus;
import com.spring6.order.model.repository.OrderRepository;
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

//    private void loadOrderData() {
//        if (orderRepository.count() == 0) {
//            orderRepository.save(Order.builder()
//                    .firstName("Kamal")
//                    .lastName("Karthik")
//                    .status(OrderStatus.PROCESSING)
//                    .build());
//        }
//    }
}
