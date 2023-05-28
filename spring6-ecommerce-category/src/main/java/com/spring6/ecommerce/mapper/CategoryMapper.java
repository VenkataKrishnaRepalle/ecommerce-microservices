package com.spring6.ecommerce.mapper;
import com.spring6.ecommerce.commonutil.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.entity.Category;
import org.mapstruct.Mapper;

@Mapper
public interface CategoryMapper {
    CategoryFindResponseDto categoryToCategoryFindResponseDto(Category category);
}
