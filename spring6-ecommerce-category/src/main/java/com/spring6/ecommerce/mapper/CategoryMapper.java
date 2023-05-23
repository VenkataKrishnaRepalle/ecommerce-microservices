package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.dto.CategoryDto;
import com.spring6.ecommerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
   Category categoryDtoToCategory(CategoryDto categoryDto);

    CategoryDto categoryToCategoryDto(Category category);
}
