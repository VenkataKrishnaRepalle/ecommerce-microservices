package com.pm.spring.ema.ecommerce;

import com.pm.spring.ema.ecommerce.repository.ProductRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

//@DataJpaTest
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@Rollback(value = false)
@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTests {

    @Mock
    private ProductRepository productRepository;

//    @Test
//    public void testCreateProduct() {
//        BrandDto brandDto = entityManager.find(BrandDto.class, "a3976462-93b7-4303-b504-0e5b0eddb283");
//        CategoryDto categoryDto = entityManager.find(CategoryDto.class, "3540536f-5468-42d4-92ff-9a576c3e7747");
//        Product product = new Product();
//        product.setName("Samsung Galaxy A31");
//        product.setShortDescription("A good smartphone from samsung");
//        product.setFullDescription("This is a Very good samrtphone full descriptiom");
//        product.setPrice(456f);
//
//
//        Product savedProduct =  productRepository.save(product);
//
//        Assertions.assertThat(savedProduct).isNotNull();
//    }

//    @Test
//    public void testSaveProductImage(){
//        String productId = "0b1cb4a8-fcf4-4d4f-9350-51e9b03dad9b";
//        Product product = productRepository.findById(UUID.fromString(productId)).get();
//
//        product.setMainImage("main image.jpg");
//        product.addExtraImage("image -1.jpg");
//        product.addExtraImage("image-2.jpg");
//        product.addExtraImage("image-3.jpg");
//
//        Product savedProduct = productRepository.save(product);
//        Assertions.assertThat(savedProduct.getImages().size()).isEqualTo(3);
//
//    }
}

