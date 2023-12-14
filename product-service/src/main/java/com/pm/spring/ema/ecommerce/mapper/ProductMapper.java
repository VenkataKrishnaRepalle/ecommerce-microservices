package com.pm.spring.ema.ecommerce.mapper;

import com.pm.spring.ema.common.dto.ProductFindResponseDto;
import com.pm.spring.ema.ecommerce.dto.ProductCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductCreateResponseDto;
import com.pm.spring.ema.ecommerce.dto.ProductUpdateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductUpdateResponseDto;
import com.pm.spring.ema.ecommerce.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {

    ProductFindResponseDto productToProductFindResponseDto(Product product);

    Product productCreateRequestDtoToProduct(ProductCreateRequestDto productCreateRequestDto);

    ProductCreateResponseDto productToProductCreateResponseDto(Product product);

    Product productUpdateRequestDtoToProduct(ProductUpdateRequestDto productUpdateRequestDto);

    ProductUpdateResponseDto productToProductUpdateResponseDto(Product product);
}
