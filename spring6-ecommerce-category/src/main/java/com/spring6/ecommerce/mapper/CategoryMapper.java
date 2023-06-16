package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
import com.spring6.ecommerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryFindResponseDto parentCategoryToParentCategoryFindResponseDto(Category category);
    Category parentCategoryUpdateRequestDtoToParentCategory(CategoryUpdateRequestDto categoryUpdateRequestDto);
    CategoryUpdateResponseDto parentCategoryToParentCategoryUpdateResponseDto(Category category);
    CategoryCreateResponseDto parentCategoryToParentCategoryCreateResponseDto(Category category);
    Category parentCategoryCreateRequestDtoToParentCategory(CategoryCreateRequestDto categoryCreateRequestDto);

}
