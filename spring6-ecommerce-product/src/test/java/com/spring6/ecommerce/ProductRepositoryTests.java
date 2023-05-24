package com.spring6.ecommerce;

import com.spring6.ecommerce.commondto.BrandDto;
import com.spring6.ecommerce.commondto.CategoryDto;
import com.spring6.ecommerce.entity.Product;
import com.spring6.ecommerce.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(value = false)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProduct() {
        BrandDto brandDto = entityManager.find(BrandDto.class, "a3976462-93b7-4303-b504-0e5b0eddb283");
        CategoryDto categoryDto = entityManager.find(CategoryDto.class, "3540536f-5468-42d4-92ff-9a576c3e7747");
        Product product = new Product();
        product.setName("Samsung Galaxy A31");
        product.setShortDescription("A good smartphone from samsung");
        product.setFullDescription("This is a Very good samrtphone full descriptiom");
        product.setPrice(456f);


        Product savedProduct =  productRepository.save(product);

        Assertions.assertThat(savedProduct).isNotNull();
    }
}

