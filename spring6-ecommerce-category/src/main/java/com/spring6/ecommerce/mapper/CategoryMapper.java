package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.commondto.CategoryDto;
import com.spring6.ecommerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    Category subCategoryDtoToSubCategory(CategoryDto categoryDto);

    CategoryDto subCategoryToSubCategoryDto(Category subCategory);
}
