package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.commondto.ParentCategoryDto;
import com.spring6.ecommerce.entity.PatentCategory;
import org.mapstruct.Mapper;

@Mapper
public interface ParentCategoryMapper {
    PatentCategory categoryDtoToCategory(ParentCategoryDto categoryDto);

    ParentCategoryDto categoryToCategoryDto(PatentCategory category);
}
