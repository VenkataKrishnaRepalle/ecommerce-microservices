package com.spring6.ecommerce.mapper;

import com.spring6.common.dto.SubCategoryFindResponseDto;
import com.spring6.ecommerce.dto.SubCategoryCreateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryCreateResponseDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateResponseDto;
import com.spring6.ecommerce.entity.SubCategory;
import org.mapstruct.Mapper;

@Mapper
public interface SubCategoryMapper {
    SubCategoryFindResponseDto subCategoryToSubCategoryFindResponseDto(SubCategory subCategory);

    SubCategory subCategoryUpdateRequestDtoToSubCategory(SubCategoryUpdateRequestDto subCategoryUpdateRequestDto);

    SubCategoryUpdateResponseDto subCategoryToSubCategoryUpdateResponseDto(SubCategory subCategory);

    SubCategoryCreateResponseDto subCategoryToSubCategoryCreateResponseDto(SubCategory subCategory);

    SubCategory subCategoryCreateRequestDtoToSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto);
}
