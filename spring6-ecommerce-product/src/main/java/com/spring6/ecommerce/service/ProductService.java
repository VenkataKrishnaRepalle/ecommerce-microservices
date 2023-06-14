package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ProductDetailsFindResponseDto;
import com.spring6.ecommerce.common.dto.ProductFindResponseDto;
import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.entity.ProductDetails;

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

    List<ProductDetailsFindResponseDto> addProductDetails(UUID productId, String[] detailNames, String[] detailValues);

//    ProductDetailsFindResponseDto isProductDetailsExists(UUID productId, String detailName, String detailvalue);

    ProductUpdateResponseDto updateProduct(UUID productId, ProductUpdateRequestDto productUpdateRequestDto);

    List<ProductFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

}
