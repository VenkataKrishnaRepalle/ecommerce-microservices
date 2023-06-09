package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.common.dto.ProductDetailsFindResponseDto;
import com.spring6.ecommerce.dto.ProductDetailsCreateRequestDto;
import com.spring6.ecommerce.dto.ProductDetailsCreateResponseDto;
import com.spring6.ecommerce.entity.ProductDetails;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface ProductDetailsMapper {
    ProductDetailsFindResponseDto productDetailsToProductDetailsFindResponseDto(ProductDetails productDetails);

    ProductDetails productDetailsFindResponseDtoToProductDetails(ProductDetailsFindResponseDto roductDetailsFindResponseDto);

    ProductDetails ProductDetailsCreateRequestDtoToProductDetails(ProductDetailsCreateRequestDto productDetailsCreateRequestDto);

    ProductDetailsCreateRequestDto ProductDetailsToProductDetailsCreateRequestDto(ProductDetails productDetails);

    ProductDetails ProductDetailsCreateResponseDtoToProductDetails(ProductDetailsCreateResponseDto productDetailsCreateResponseDto);

    ProductDetailsCreateResponseDto ProductDetailsToProductDetailsCreateResponseDto(ProductDetails productDetails);
}
