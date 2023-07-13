package com.spring6.brand.model.repository;

import com.spring6.brand.model.entity.Brand;
import com.spring6.common.enums.BrandStatusEnum;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testCreateBrand() {
        Brand savedBrand = brandRepository.save(getBrand());

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isNotNull();

    }
    private Brand getBrand() {
        return Brand.builder()
                .name("Acer")
                .imageName("Acer.png")
                .status(BrandStatusEnum.ACTIVE)
                .subcategoryId(UUID.randomUUID())
                .build();
    }

}