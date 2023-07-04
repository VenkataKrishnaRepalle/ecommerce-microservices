package com.spring6.ecommerce.mapper;

import com.spring6.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
import com.spring6.ecommerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryFindResponseDto categoryToCategoryFindResponseDto(Category category);
    Category categoryUpdateRequestDtoToCategory(CategoryUpdateRequestDto categoryUpdateRequestDto);
    CategoryUpdateResponseDto categoryToCategoryUpdateResponseDto(Category category);
    CategoryCreateResponseDto categoryToCategoryCreateResponseDto(Category category);
    Category categoryCreateRequestDtoToCategory(CategoryCreateRequestDto categoryCreateRequestDto);

}
