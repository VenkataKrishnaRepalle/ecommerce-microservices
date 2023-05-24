package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commondto.ProductFindResponseDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductService {

    List<ProductFindResponseDto> listAll();

    ProductFindResponseDto getProductById(UUID productId);
}
