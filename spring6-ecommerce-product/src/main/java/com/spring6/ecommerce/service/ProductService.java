package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ProductFindResponseDto;
import com.spring6.ecommerce.dto.ProductCreateRequestDto;
import com.spring6.ecommerce.dto.ProductCreateResponseDto;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductFindResponseDto> listAll();

    ProductFindResponseDto getProductById(UUID productId);

    ProductCreateResponseDto addProduct(ProductCreateRequestDto productCreateRequestDto);

    void updateProductEnabledStatus(UUID productId, boolean status);

    void deleteProductById(UUID productId);

    boolean isProductNameExists(String productName);

    boolean isProductExists(UUID productId);

    void updateImageName(UUID productId, String fileName);
}
