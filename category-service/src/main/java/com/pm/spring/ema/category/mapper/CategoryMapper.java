package com.pm.spring.ema.category.mapper;

import com.pm.spring.ema.category.common.dto.categoryDto.request.CategoryCreateRequestDto;
import com.pm.spring.ema.category.common.dto.categoryDto.request.CategoryUpdateRequestDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryCreateResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryDeleteResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryFindResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryUpdateResponseDto;
import com.pm.spring.ema.category.model.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryFindResponseDto categoryToCategoryFindResponseDto(Category category);
    Category categoryUpdateRequestDtoToCategory(CategoryUpdateRequestDto categoryUpdateRequestDto);
    CategoryUpdateResponseDto categoryToCategoryUpdateResponseDto(Category category);
    CategoryCreateResponseDto categoryToCategoryCreateResponseDto(Category category);
    Category categoryCreateRequestDtoToCategory(CategoryCreateRequestDto categoryCreateRequestDto);
    CategoryDeleteResponseDto convertToCategoryDeleteResponseDto(Category category);

}
