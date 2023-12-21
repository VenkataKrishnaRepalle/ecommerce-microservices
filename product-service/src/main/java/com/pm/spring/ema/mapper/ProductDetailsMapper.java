package com.pm.spring.ema.mapper;

import com.pm.spring.ema.common.dto.ProductDetailsFindResponseDto;
import com.pm.spring.ema.dto.ProductDetailsCreateRequestDto;
import com.pm.spring.ema.dto.ProductDetailsUpdateRequestDto;
import com.pm.spring.ema.model.entity.ProductDetails;
import org.mapstruct.Mapper;

@Mapper
public interface ProductDetailsMapper {
    ProductDetailsFindResponseDto productDetailsToProductDetailsFindResponseDto(ProductDetails productDetails);

    ProductDetails productDetailsFindResponseDtoToProductDetails(ProductDetailsFindResponseDto roductDetailsFindResponseDto);

    ProductDetails ProductDetailsCreateRequestDtoToProductDetails(ProductDetailsCreateRequestDto productDetailsCreateRequestDto);

    ProductDetailsCreateRequestDto ProductDetailsToProductDetailsCreateRequestDto(ProductDetails productDetails);

    ProductDetails ProductDetailsUpdateRequestDtoToProductDetails(ProductDetailsUpdateRequestDto productDetailsUpdateRequestDto);

    ProductDetailsUpdateRequestDto ProductDetailsToProductDetailsUpdateRequestDto(ProductDetails productDetails);
}
