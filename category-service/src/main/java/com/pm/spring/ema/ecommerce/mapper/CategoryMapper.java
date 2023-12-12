package com.pm.spring.ema.ecommerce.mapper;

import com.pm.spring.ema.common.dto.CategoryFindResponseDto;
import com.pm.spring.ema.ecommerce.dto.CategoryCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.CategoryCreateResponseDto;
import com.pm.spring.ema.ecommerce.dto.CategoryUpdateRequestDto;
import com.pm.spring.ema.ecommerce.dto.CategoryUpdateResponseDto;
import com.pm.spring.ema.ecommerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryFindResponseDto categoryToCategoryFindResponseDto(Category category);
    Category categoryUpdateRequestDtoToCategory(CategoryUpdateRequestDto categoryUpdateRequestDto);
    CategoryUpdateResponseDto categoryToCategoryUpdateResponseDto(Category category);
    CategoryCreateResponseDto categoryToCategoryCreateResponseDto(Category category);
    Category categoryCreateRequestDtoToCategory(CategoryCreateRequestDto categoryCreateRequestDto);

}
