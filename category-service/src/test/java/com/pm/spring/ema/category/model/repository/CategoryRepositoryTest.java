package com.pm.spring.ema.category.model.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.pm.spring.ema.category.model.Category;
import com.pm.spring.ema.category.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class CategoryRepositoryTest {
  @Autowired private CategoryRepository categoryRepository;

  @Test
  public void testCreateRootCategory() {

    Category savedCategory =
        categoryRepository.save(
            Category.builder().name("Computer").alias("Computer").imageName("defalut.png").build());

    assertThat(savedCategory).isNotNull();
    assertThat(savedCategory.getId()).isNotNull();
  }
}
