package com.spring6.ecommerce.mapper;
import com.spring6.ecommerce.dto.SubCategoryDto;
import com.spring6.ecommerce.entity.SubCategory;
import org.mapstruct.Mapper;

@Mapper
public interface SubCategoryMapper {
    SubCategory subCategoryDtoToSubCategory(SubCategoryDto categoryDto);
    SubCategoryDto subCategoryToSubCategoryDto(SubCategory subCategory);
}
