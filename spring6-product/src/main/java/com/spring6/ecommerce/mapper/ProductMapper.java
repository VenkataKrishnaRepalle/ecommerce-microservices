package com.spring6.ecommerce.mapper;

import com.spring6.common.dto.ProductFindResponseDto;
import com.spring6.ecommerce.dto.ProductCreateRequestDto;
import com.spring6.ecommerce.dto.ProductCreateResponseDto;
import com.spring6.ecommerce.dto.ProductUpdateRequestDto;
import com.spring6.ecommerce.dto.ProductUpdateResponseDto;
import com.spring6.ecommerce.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductFindResponseDto productToProductFindResponseDto(Product product);

    Product productCreateRequestDtoToProduct(ProductCreateRequestDto productCreateRequestDto);

    ProductCreateResponseDto productToProductCreateResponseDto(Product product);

    Product productUpdateRequestDtoToProduct(ProductUpdateRequestDto productUpdateRequestDto);

    ProductUpdateResponseDto productToProductUpdateResponseDto(Product product);
}
