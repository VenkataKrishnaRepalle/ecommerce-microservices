package com.spring6.ecommerce.bootstrap;

import com.spring6.ecommerce.entity.ParentCategory;
import com.spring6.ecommerce.entity.Category;
import com.spring6.ecommerce.repository.ParentCategoryRepository;
import com.spring6.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final ParentCategoryRepository parentCategoryRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public void run(String... args) throws Exception {
        loadCategoryAndSubCategoryData();
    }

    private void loadCategoryAndSubCategoryData() {
        if (parentCategoryRepository.count() == 0) {
            ParentCategory computer = parentCategoryRepository.save(ParentCategory.builder()
                    .name("Computer")
                    .alias("Computer")
                    .image("Computer.png")
                    .isEnabled(Boolean.TRUE)
                    .build());
            ParentCategory electronics = parentCategoryRepository.save(ParentCategory.builder()
                    .name("Electronics")
                    .alias("Electronics")
                    .image("Electronics.png")
                    .isEnabled(Boolean.TRUE)
                    .build());


            categoryRepository.save(Category.builder()
                    .name("Desktops")
                    .alias("Desktops")
                    .image("Desktops.png")
                    .parentCategory(computer)
                    .isEnabled(Boolean.TRUE)
                    .build());
            categoryRepository.save(Category.builder()
                    .name("Laptops")
                    .alias("Laptops")
                    .image("Laptops.png")
                    .parentCategory(computer)
                    .isEnabled(Boolean.TRUE)
                    .build());

            categoryRepository.save(Category.builder()
                    .name("Cameras")
                    .alias("Cameras")
                    .image("Cameras.png")
                    .parentCategory(electronics)
                    .isEnabled(Boolean.TRUE)
                    .build());

            categoryRepository.save(Category.builder()
                    .name("SmartPhones")
                    .alias("Smart Phones")
                    .image("SmartPhones.png")
                    .parentCategory(electronics)
                    .isEnabled(Boolean.TRUE)
                    .build());
        }


    }

}
