package com.pm.spring.ema.bootstrap;

import com.pm.spring.ema.model.entity.Product;
import com.pm.spring.ema.model.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBrandData();
    }

    private void loadBrandData() {
        if (productRepository.count() == 0) {

            productRepository.save(Product.builder()
                    .name("OnePlus Nord")
                    .alias("CE")
                    .shortDescription("very Good Phone")
                    .fullDescription("Very good Phone at low price")
                    .inStock(true)
                    .cost(BigDecimal.valueOf(22999))
                    .price(BigDecimal.valueOf(19999))
                    .discountPercent(BigDecimal.valueOf(22999).subtract(BigDecimal.valueOf(19999).divide(BigDecimal.valueOf(22999).multiply(BigDecimal.valueOf(100)))))
                    .length(3.5f)
                    .width(1.2f)
                    .height(6.2f)
                    .weight(12.5f)
                    .mainImage("11.jpg")
                    .categoryId(UUID.fromString("3540536f-5468-42d4-92ff-9a576c3e7747"))
                    .brandId(UUID.fromString("a3976462-93b7-4303-b504-0e5b0eddb283"))
                    .isEnabled(Boolean.TRUE)
                    .build());
            productRepository.save(Product.builder()
                    .name("Realme")
                    .alias("Note 4")
                    .shortDescription("very Good Phone")
                    .fullDescription("Very good Phone at low price")
                    .inStock(true)
                    .cost(BigDecimal.valueOf(22999))
                    .price(BigDecimal.valueOf(19999))
                    .discountPercent(BigDecimal.valueOf(22999).subtract(BigDecimal.valueOf(19999).divide(BigDecimal.valueOf(22999).multiply(BigDecimal.valueOf(100)))))
                    .length(3.5f)
                    .width(1.2f)
                    .height(6.2f)
                    .weight(12.5f)
                    .mainImage("11.jpg")
                    .categoryId(UUID.fromString("3540536f-5468-42d4-92ff-9a576c3e7747"))
                    .brandId(UUID.fromString("a3976462-93b7-4303-b504-0e5b0eddb283"))
                    .isEnabled(Boolean.TRUE)
                    .build());
            productRepository.save(Product.builder()
                    .name("Poco")
                    .alias("x7")
                    .shortDescription("very Good Phone")
                    .fullDescription("Very good Phone at low price")
                    .inStock(true)
                    .cost(BigDecimal.valueOf(22999))
                    .price(BigDecimal.valueOf(19999))
                    .discountPercent(BigDecimal.valueOf(22999).subtract(BigDecimal.valueOf(19999).divide(BigDecimal.valueOf(22999).multiply(BigDecimal.valueOf(100)))))
                    .length(3.5f)
                    .width(1.2f)
                    .height(6.2f)
                    .weight(12.5f)
                    .mainImage("11.jpg")
                    .categoryId(UUID.fromString("3540536f-5468-42d4-92ff-9a576c3e7747"))
                    .brandId(UUID.fromString("a3976462-93b7-4303-b504-0e5b0eddb283"))
                    .isEnabled(Boolean.TRUE)
                    .build());
        }
    }
}
