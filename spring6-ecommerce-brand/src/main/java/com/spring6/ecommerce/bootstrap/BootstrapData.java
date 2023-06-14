package com.spring6.ecommerce.bootstrap;

import com.spring6.ecommerce.entity.Brand;
import com.spring6.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class BootstrapData implements CommandLineRunner {
    private final BrandRepository brandRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBrandData();
    }

    private void loadBrandData() {
        if (brandRepository.count() == 0) {
            brandRepository.save(Brand.builder()
                    .name("Acer")
                    .logo("Acer.png")
                    .isEnabled(Boolean.TRUE)
                    .build());
            brandRepository.save(Brand.builder()
                    .name("Samsung")
                    .logo("Samsung.png")
                    .isEnabled(Boolean.TRUE)
                    .build());
            brandRepository.save(Brand.builder()
                    .name("Canon")
                    .logo("Canon.png")
                    .isEnabled(Boolean.TRUE)
                    .build());
        }
    }
}
