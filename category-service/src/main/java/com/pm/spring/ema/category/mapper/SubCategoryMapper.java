package com.pm.spring.ema.category.mapper;

import com.pm.spring.ema.category.common.dto.subcategoryDto.request.SubCategoryCreateRequestDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.request.SubCategoryUpdateRequestDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryCreateResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryDeleteResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryFindResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryUpdateResponseDto;
import com.pm.spring.ema.category.model.entity.SubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface SubCategoryMapper {
    @Mapping(source = "category.id", target = "CategoryId")
    SubCategoryFindResponseDto subCategoryToSubCategoryFindResponseDto(SubCategory subCategory);

    SubCategory subCategoryUpdateRequestDtoToSubCategory(SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

    SubCategoryUpdateResponseDto subCategoryToSubCategoryUpdateResponseDto(SubCategory subCategory);

    SubCategoryCreateResponseDto subCategoryToSubCategoryCreateResponseDto(SubCategory subCategory);

    SubCategory subCategoryCreateRequestDtoToSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto);

    SubCategoryDeleteResponseDto convertToSubCategoryDeleteResponseDto(SubCategory subCategory);
}
