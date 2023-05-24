package com.spring6.ecommerce.repository;

import com.spring6.ecommerce.entity.PatentCategory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class PatentParentCategoryRepositoryTest {
    @Autowired
    private ParentCategoryRepository categoryRepository;

    @Test
    public void testCreateRootCategory() {


        PatentCategory savedCategory = categoryRepository.save(PatentCategory.builder()
                .name("Computer")
                .alias("Computer")
                .image("defalut.png")
                .build());

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getId()).isNotNull();
    }
}
