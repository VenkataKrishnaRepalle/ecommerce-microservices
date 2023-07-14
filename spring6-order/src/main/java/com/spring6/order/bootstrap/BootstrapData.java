package com.spring6.order.bootstrap;

import com.spring6.order.model.entity.Order;
import com.spring6.order.model.repository.OrderRepository;
import com.spring6.common.enums.BrandStatusEnum;
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
        loadBrandData();
    }

    private void loadBrandData() {
        if (orderRepository.count() == 0) {
            orderRepository.save(Order.builder()
                    .name("Acer")
                    .imageName("Acer.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("Samsung")
                    .imageName("Samsung.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("Canon")
                    .imageName("Canon.png")
                    .status(BrandStatusEnum.INACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("TVS")
                    .imageName("tvs.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("Honda")
                    .imageName("honda.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("Apple")
                    .imageName("apple.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("Xiomi")
                    .imageName("xiomi.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("mi")
                    .imageName("mi.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("Philips")
                    .imageName("philps.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("Voltas")
                    .imageName("voltas.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("Pixma")
                    .imageName("pixma.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());

            orderRepository.save(Order.builder()
                    .name("Singer")
                    .imageName("singer.png")
                    .status(BrandStatusEnum.ACTIVE)
                    .subcategoryId(UUID.randomUUID())
                    .build());
        }
    }
}
