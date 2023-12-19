package com.pm.spring.ema.category.bootstrap;
import com.pm.spring.ema.category.common.enums.CategoryEnum;
import com.pm.spring.ema.category.common.enums.SubCategoryEnum;
import com.pm.spring.ema.category.model.dao.categoryDAO.CategoryDAO;
import com.pm.spring.ema.category.model.dao.subCategoryDAO.SubCategoryDAO;
import com.pm.spring.ema.category.model.entity.SubCategory;
import com.pm.spring.ema.category.model.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryDAO categoryDao;
    private final SubCategoryDAO subCategoryDao;

    @Override
    public void run(String... args) throws Exception {
        loadCategoryAndSubCategoryData();
    }

    private void loadCategoryAndSubCategoryData() {
        if (categoryDao.count() == 0) {
            Category computer = categoryDao.save(Category.builder()
                    .name("Computer")
                    .alias("Computer")
                    .imageName("Computer.png")
                    .status(CategoryEnum.ACTIVE)
                    .build());
            Category electronics = categoryDao.save(Category.builder()
                    .name("Electronics")
                    .alias("Electronics")
                    .imageName("Electronics.png")
                    .status(CategoryEnum.ACTIVE)
                    .build());


            subCategoryDao.save(SubCategory.builder()
                    .name("Desktops")
                    .alias("Desktops")
                    .imageName("Desktops.png")
                    .category(computer)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());
            subCategoryDao.save(SubCategory.builder()
                    .name("Laptops")
                    .alias("Laptops")
                    .imageName("Laptops.png")
                    .category(computer)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());

            subCategoryDao.save(SubCategory.builder()
                    .name("Cameras")
                    .alias("Cameras")
                    .imageName("Cameras.png")
                    .category(electronics)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());

            subCategoryDao.save(SubCategory.builder()
                    .name("SmartPhones")
                    .alias("Smart Phones")
                    .imageName("SmartPhones.png")
                    .category(electronics)
                    .status(SubCategoryEnum.ACTIVE)
                    .build());
        }


    }

}
