package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.dto.ProductCreateRequestDto;
import com.spring6.ecommerce.dto.ProductImageCreateRequestDto;
import com.spring6.ecommerce.dto.ProductImageCreateResponseDto;
import com.spring6.ecommerce.dto.ProductImageFindResponseDto;
import com.spring6.ecommerce.entity.ProductImage;
import org.mapstruct.Mapper;

@Mapper
public interface ProductImageMapper {

    ProductImageFindResponseDto ProductImageToProductImageFindResponseDto(ProductImage productImage);

    ProductImage ProductImageCreateRequestDtoToProductImage(ProductImageCreateRequestDto productCreateRequestDto);

    ProductCreateRequestDto ProductImageToProductImageCreateRequestDto(ProductImage productImage);

    ProductImage ProductImageCreateResponseDtoToProductImage(ProductImageCreateResponseDto productImageCreateResponseDto);

    ProductImageCreateResponseDto ProductImageToProductImageCreateResponseDto(ProductImage productImage);
}
