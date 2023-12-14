package com.pm.spring.ema.category.mapper;

import com.pm.spring.ema.common.dto.SubCategoryFindResponseDto;
import com.pm.spring.ema.category.dto.subcategoryDto.SubCategoryCreateRequestDto;
import com.pm.spring.ema.category.dto.subcategoryDto.SubCategoryCreateResponseDto;
import com.pm.spring.ema.category.dto.subcategoryDto.SubCategoryUpdateRequestDto;
import com.pm.spring.ema.category.dto.subcategoryDto.SubCategoryUpdateResponseDto;
import com.pm.spring.ema.category.model.entity.SubCategory;
import org.mapstruct.Mapper;

@Mapper
public interface SubCategoryMapper {
    SubCategoryFindResponseDto subCategoryToSubCategoryFindResponseDto(SubCategory subCategory);

    SubCategory subCategoryUpdateRequestDtoToSubCategory(SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

    SubCategoryUpdateResponseDto subCategoryToSubCategoryUpdateResponseDto(SubCategory subCategory);

    SubCategoryCreateResponseDto subCategoryToSubCategoryCreateResponseDto(SubCategory subCategory);

    SubCategory subCategoryCreateRequestDtoToSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto);
}
