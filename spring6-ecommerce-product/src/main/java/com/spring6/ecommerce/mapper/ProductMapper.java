package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.dto.ProductDto;
import com.spring6.ecommerce.entity.Product;
import org.mapstruct.Mapper;

@Mapper
public interface ProductMapper {
    Product productDtoToProduct(ProductDto productDto);

    ProductDto productToProductDto(Product product);
}
