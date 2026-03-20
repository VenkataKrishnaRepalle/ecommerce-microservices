package com.pm.spring.ema.brand.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.pm.spring.ema.brand.entity.Brand;
import com.pm.spring.ema.common.util.enums.BrandStatusEnum;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class BrandRepositoryTest {

  @Autowired private BrandRepository brandRepository;

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
        .subCategoryId(UUID.randomUUID())
        .build();
  }
}
