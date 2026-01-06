package com.pm.spring.ema.category.mapper;

import com.pm.spring.ema.category.model.Category;
import com.pm.spring.ema.common.util.dto.CategoryDto;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryDto toCategoryDto(Category category);

    Category toCategory(CategoryDto CategoryRequestDto);
}
