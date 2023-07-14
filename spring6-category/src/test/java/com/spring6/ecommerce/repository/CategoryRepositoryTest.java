package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void testCreateRootCategory() {


        Category savedCategory = categoryRepository.save(Category.builder()
                .name("Computer")
                .alias("Computer")
                .image("defalut.png")
                .build());

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isNotNull();
    }
}
