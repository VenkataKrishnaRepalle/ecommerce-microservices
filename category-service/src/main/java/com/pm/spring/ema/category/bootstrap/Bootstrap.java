package com.pm.spring.ema.category.bootstrap;
import com.pm.spring.ema.category.model.SubCategory;
import com.pm.spring.ema.category.model.Category;
import com.pm.spring.ema.category.repository.CategoryRepository;
import com.pm.spring.ema.category.repository.SubCategoryRepository;
import com.pm.spring.ema.common.util.enums.CategoryEnum;
import com.pm.spring.ema.common.util.enums.SubCategoryEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;


    @Override
    public void run(String... args) throws Exception {
        loadCategoryAndSubCategoryData();
    }

    private void loadCategoryAndSubCategoryData() {
        if (categoryRepository.count() == 0) {
            Category computer = categoryRepository.save(Category.builder()
                    .name("Computer")
                    .alias("Computer")
                    .imageName("Computer.png")
                    .status(CategoryEnum.ACTIVE)
                    .build());
            Category electronics = categoryRepository.save(Category.builder()
                    .name("Electronics")
                    .alias("Electronics")
                    .imageName("Electronics.png")
                    .status(CategoryEnum.ACTIVE)
                    .build());


            subCategoryRepository.save(SubCategory.builder()
                    .name("Desktops")
                    .alias("Desktops")
                    .imageName("Desktops.png")
                    .category(computer)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());
            subCategoryRepository.save(SubCategory.builder()
                    .name("Laptops")
                    .alias("Laptops")
                    .imageName("Laptops.png")
                    .category(computer)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());

            subCategoryRepository.save(SubCategory.builder()
                    .name("Cameras")
                    .alias("Cameras")
                    .imageName("Cameras.png")
                    .category(electronics)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());

            subCategoryRepository.save(SubCategory.builder()
                    .name("SmartPhones")
                    .alias("Smart Phones")
                    .imageName("SmartPhones.png")
                    .category(electronics)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());
        }


    }

}
