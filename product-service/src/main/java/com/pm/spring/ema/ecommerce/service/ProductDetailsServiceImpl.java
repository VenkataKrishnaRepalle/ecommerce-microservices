package com.pm.spring.ema.ecommerce.service;

import com.pm.spring.ema.common.dto.ProductDetailsFindResponseDto;
import com.pm.spring.ema.ecommerce.dto.ProductDetailsCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductDetailsUpdateRequestDto;
import com.pm.spring.ema.ecommerce.entity.ProductDetails;
import com.pm.spring.ema.ecommerce.exception.ErrorCode;
import com.pm.spring.ema.ecommerce.exception.InvalidInputDetailsException;
import com.pm.spring.ema.ecommerce.exception.ProductAlreadyPresentException;
import com.pm.spring.ema.ecommerce.exception.ProductNotFoundException;
import com.pm.spring.ema.ecommerce.mapper.ProductDetailsMapper;
import com.pm.spring.ema.ecommerce.repository.ProductDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsRepository productDetailsRepository;

    private final ProductDetailsMapper productDetailsMapper;

    public List<ProductDetailsFindResponseDto> create(final UUID id, final String[] detailNames, final String[] detailValues) {
        List<ProductDetailsCreateRequestDto> productDetails = new ArrayList<>();

        for (int count = 0; count < detailNames.length; count++) {
            if (!detailNames[count].isEmpty() && !detailValues[count].isEmpty()) {
                ProductDetailsCreateRequestDto isProductDetailsExists = productDetailsMapper.ProductDetailsToProductDetailsCreateRequestDto(productDetailsRepository.isProductDetailsExists(id, detailNames[count]));
                if (isProductDetailsExists != null) {
                    throw new ProductAlreadyPresentException(ErrorCode.E1503.getCode(), detailNames[count]);
                }
                ProductDetailsCreateRequestDto productDetailsRequestDto = new ProductDetailsCreateRequestDto();
                productDetailsRequestDto.setName(detailNames[count]);
                productDetailsRequestDto.setValue(detailValues[count]);
                productDetailsRequestDto.setProductId(id);

                productDetails.add(productDetailsRequestDto);
            }
        }
        List<ProductDetailsFindResponseDto> productDetailsFindResponseDtoList = new ArrayList<>();

        for (ProductDetailsCreateRequestDto productDetail : productDetails) {
            ProductDetailsFindResponseDto finalProductDetails = productDetailsMapper.productDetailsToProductDetailsFindResponseDto(productDetailsRepository.save(productDetailsMapper.ProductDetailsCreateRequestDtoToProductDetails(productDetail)));
            productDetailsFindResponseDtoList.add(finalProductDetails);
        }
        return productDetailsFindResponseDtoList;
    }

    public List<ProductDetailsFindResponseDto> update(UUID id, String[] detailName, String[] detailValue) {

        List<ProductDetails> listProductDetails = new ArrayList<>();

        boolean isProductDetailsExists = isProductDetailsExitsWithProductId(id);

        if (isProductDetailsExists) {
            for (int index = 0; index < detailName.length; index++) {
                if (!detailName[index].isEmpty() && !detailValue[index].isEmpty()) {
                    ProductDetails productDetails = productDetailsRepository.isProductDetailsExists(id, detailName[index]);
                    if (productDetails == null) {
                        ProductDetailsUpdateRequestDto productDetails1 = new ProductDetailsUpdateRequestDto();
                        productDetails1.setProductId(id);
                        productDetails1.setName(detailName[index]);
                        productDetails1.setValue(detailValue[index]);

                        ProductDetails pd = productDetailsRepository.save(productDetailsMapper.ProductDetailsUpdateRequestDtoToProductDetails(productDetails1));

                        listProductDetails.add(pd);
                    } else {
                        productDetails.setValue(detailValue[index]);
                        productDetailsRepository.save(productDetails);
                        listProductDetails.add(productDetails);
                    }
                } else if(detailName[index].isEmpty()){
                    throw new InvalidInputDetailsException(ErrorCode.E1002.getCode(), String.valueOf(detailName));
                }
            }
        } else {
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(id));
        }

        List<ProductDetailsFindResponseDto> productDetailsFindResponseDtoList = new ArrayList<>();

        for (ProductDetails listProductDetail : listProductDetails) {
            ProductDetailsFindResponseDto finalProductDetails = productDetailsMapper.productDetailsToProductDetailsFindResponseDto(listProductDetail);
            productDetailsFindResponseDtoList.add(finalProductDetails);
        }
        return productDetailsFindResponseDtoList;
    }

    @Override
    public List<ProductDetailsFindResponseDto> getByProductId(UUID id) {
        if (!isProductDetailsExitsWithProductId(id)) {
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(),String.valueOf(id));
        }
        return productDetailsRepository.getByProductId(id)
                .stream()
                .map(productDetailsMapper::productDetailsToProductDetailsFindResponseDto).toList();
    }

    public boolean isProductDetailsExitsWithProductId(UUID productId) {
        if (!productDetailsRepository.isProductDetailsExistsWithProductId(productId)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
