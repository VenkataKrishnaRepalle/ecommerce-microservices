package com.pm.spring.ema.category.mapper;

import com.pm.spring.ema.common.dto.CategoryFindResponseDto;
import com.pm.spring.ema.category.dto.categoryDto.CategoryCreateRequestDto;
import com.pm.spring.ema.category.dto.categoryDto.CategoryCreateResponseDto;
import com.pm.spring.ema.category.dto.categoryDto.CategoryUpdateRequestDto;
import com.pm.spring.ema.category.dto.categoryDto.CategoryUpdateResponseDto;
import com.pm.spring.ema.category.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryFindResponseDto categoryToCategoryFindResponseDto(Category category);
    Category categoryUpdateRequestDtoToCategory(CategoryUpdateRequestDto categoryUpdateRequestDto);
    CategoryUpdateResponseDto categoryToCategoryUpdateResponseDto(Category category);
    CategoryCreateResponseDto categoryToCategoryCreateResponseDto(Category category);
    Category categoryCreateRequestDtoToCategory(CategoryCreateRequestDto categoryCreateRequestDto);

}
