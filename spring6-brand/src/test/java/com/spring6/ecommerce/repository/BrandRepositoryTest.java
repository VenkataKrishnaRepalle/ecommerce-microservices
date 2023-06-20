package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testCreateBrand() {
        Brand savedBrand = brandRepository.save(Brand.builder()
                .name("Acer")
                .logo("Acer.png")
                .build());

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isNotNull();

    }

}