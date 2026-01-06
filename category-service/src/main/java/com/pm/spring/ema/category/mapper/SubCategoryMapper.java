package com.pm.spring.ema.category.mapper;

import com.pm.spring.ema.category.model.SubCategory;
import com.pm.spring.ema.common.util.dto.SubCategoryDto;
import org.mapstruct.Mapper;

@Mapper
public interface SubCategoryMapper {
    SubCategoryDto toSubCategoryDto(SubCategory subCategory);

    SubCategory toSubCategory(SubCategoryDto subCategory);
}
