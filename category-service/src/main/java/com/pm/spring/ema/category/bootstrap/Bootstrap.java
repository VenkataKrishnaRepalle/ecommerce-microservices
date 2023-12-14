package com.pm.spring.ema.category.bootstrap;
import com.pm.spring.ema.category.model.dao.categoryDao.CategoryDao;
import com.pm.spring.ema.category.model.dao.subCategoryDao.SubCategoryDao;
import com.pm.spring.ema.common.enums.CategoryEnum;
import com.pm.spring.ema.common.enums.SubCategoryEnum;
import com.pm.spring.ema.category.model.entity.SubCategory;
import com.pm.spring.ema.category.model.entity.Category;
import com.pm.spring.ema.category.model.repository.CategoryRepository;
import com.pm.spring.ema.category.model.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryDao categoryDao;
    private final SubCategoryDao subCategoryDao;

    @Override
    public void run(String... args) throws Exception {
        loadCategoryAndSubCategoryData();
    }

    private void loadCategoryAndSubCategoryData() {
        if (categoryDao.count() == 0) {
            Category computer = categoryDao.save(Category.builder()
                    .name("Computer")
                    .alias("Computer")
                    .image("Computer.png")
                    .status(CategoryEnum.ACTIVE)
                    .build());
            Category electronics = categoryDao.save(Category.builder()
                    .name("Electronics")
                    .alias("Electronics")
                    .image("Electronics.png")
                    .status(CategoryEnum.ACTIVE)
                    .build());


            subCategoryDao.save(SubCategory.builder()
                    .name("Desktops")
                    .alias("Desktops")
                    .image("Desktops.png")
                    .category(computer)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());
            subCategoryDao.save(SubCategory.builder()
                    .name("Laptops")
                    .alias("Laptops")
                    .image("Laptops.png")
                    .category(computer)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());

            subCategoryDao.save(SubCategory.builder()
                    .name("Cameras")
                    .alias("Cameras")
                    .image("Cameras.png")
                    .category(electronics)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());

            subCategoryDao.save(SubCategory.builder()
                    .name("SmartPhones")
                    .alias("Smart Phones")
                    .image("SmartPhones.png")
                    .category(electronics)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());
        }


    }

}
