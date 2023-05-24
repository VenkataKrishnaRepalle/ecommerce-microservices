package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commondto.ProductFindResponseDto;
import com.spring6.ecommerce.dto.ProductCreateRequestDto;
import com.spring6.ecommerce.dto.ProductCreateResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    List<ProductFindResponseDto> listAll();

    ProductFindResponseDto getProductById(UUID productId);

    ProductCreateResponseDto addProduct(ProductCreateRequestDto productCreateRequestDto);
}
