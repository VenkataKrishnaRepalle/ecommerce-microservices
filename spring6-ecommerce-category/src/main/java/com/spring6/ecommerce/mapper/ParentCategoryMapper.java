package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.common.dto.ParentCategoryFindResponseDto;
import com.spring6.ecommerce.dto.ParentCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.ParentCategoryUpdateResponseDto;
import com.spring6.ecommerce.entity.ParentCategory;
import org.mapstruct.Mapper;

@Mapper
public interface ParentCategoryMapper {
    ParentCategoryFindResponseDto parentCategoryToParentCategoryFindResponseDto(ParentCategory parentCategory);
    ParentCategory parentCategoryUpdateRequestDtoToParentCategory(ParentCategoryUpdateRequestDto parentCategoryUpdateRequestDto);
    ParentCategoryUpdateResponseDto parentCategoryToParentCategoryUpdateResponseDto(ParentCategory parentCategory);

}
