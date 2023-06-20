package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ProductDetailsFindResponseDto;
import com.spring6.ecommerce.common.dto.ProductFindResponseDto;
import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.entity.ProductDetails;

import java.util.List;
import java.util.UUID;

public interface ProductService {

    List<ProductFindResponseDto> listAll();

    ProductFindResponseDto getProductById(UUID id);

    ProductCreateResponseDto create(ProductCreateRequestDto productCreateRequestDto);

    ProductUpdateResponseDto update(UUID id, ProductUpdateRequestDto productUpdateRequestDto);

    void updateProductStatusById(UUID id, boolean status);

    void deleteProductById(UUID id);

    boolean isProductNameExists(String productName);

    boolean isProductExists(UUID id);

    void uploadImage(UUID id, String fileName);

    List<ProductFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    List<ProductFindResponseDto> getByCategoryId(UUID categoryId);

    List<ProductFindResponseDto> getByBrandId(UUID brandId);

    List<ProductFindResponseDto> getByCategoryIdAndBrandId(UUID categoryId, UUID brandId);

}