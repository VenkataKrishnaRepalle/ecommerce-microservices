package com.spring6.ecommerce.bootstrap;

import com.spring6.ecommerce.entity.Product;
import com.spring6.ecommerce.mapper.ProductMapper;
import com.spring6.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
                    .cost(22999f)
                    .price(19999f)
                    .discountPercent(((22999f - 19999f) / 22999f) * 100)
                    .length(3.5f)
                    .width(1.2f)
                    .height(6.2f)
                    .isEnabled(Boolean.TRUE)
                    .build());
            productRepository.save(Product.builder()
                    .name("Realme")
                    .alias("Note 4")
                    .shortDescription("very Good Phone")
                    .fullDescription("Very good Phone at low price")
                    .inStock(true)
                    .cost(19999f)
                    .price(16999f)
                    .discountPercent(((19999f - 16999f) / 19999f) * 100)
                    .length(3.5f)
                    .width(1.2f)
                    .height(6.2f)
                    .isEnabled(Boolean.TRUE)
                    .build());
            productRepository.save(Product.builder()
                    .name("Poco")
                    .alias("x7")
                    .shortDescription("very Good Phone")
                    .fullDescription("Very good Phone at low price")
                    .inStock(true)
                    .cost(24999f)
                    .price(11999f)
                    .discountPercent(((24999f - 21999f) / 24999f) * 100)
                    .length(3.5f)
                    .width(1.2f)
                    .height(6.2f)
                    .isEnabled(Boolean.TRUE)
                    .build());
        }
    }
}
