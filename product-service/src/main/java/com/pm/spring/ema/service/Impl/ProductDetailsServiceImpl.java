package com.pm.spring.ema.permission.Impl;

import com.pm.spring.ema.common.dto.ProductDetailsFindResponseDto;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.dto.ProductDetailsCreateRequestDto;
import com.pm.spring.ema.dto.ProductDetailsUpdateRequestDto;
import com.pm.spring.ema.model.dao.ProductDetailsDao;
import com.pm.spring.ema.model.entity.ProductDetails;
import com.pm.spring.ema.exception.InvalidInputDetailsException;
import com.pm.spring.ema.exception.ProductAlreadyPresentException;
import com.pm.spring.ema.exception.ProductNotFoundException;
import com.pm.spring.ema.dto.mapper.ProductDetailsMapper;
import com.pm.spring.ema.permission.ProductDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsDao productDetailsDao;

    private final ProductDetailsMapper productDetailsMapper;

    public List<ProductDetailsFindResponseDto> create(final UUID id, final String[] detailNames, final String[] detailValues) {
        List<ProductDetailsCreateRequestDto> productDetails = new ArrayList<>();

        for (int count = 0; count < detailNames.length; count++) {
            if (!detailNames[count].isEmpty() && !detailValues[count].isEmpty()) {
                ProductDetailsCreateRequestDto isProductDetailsExists = productDetailsMapper.ProductDetailsToProductDetailsCreateRequestDto(productDetailsDao.isProductDetailsExists(id, detailNames[count]));
                if (isProductDetailsExists != null) {
                    throw new ProductAlreadyPresentException(ErrorCodes.E2002, detailNames[count]);
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
            ProductDetailsFindResponseDto finalProductDetails = productDetailsMapper.productDetailsToProductDetailsFindResponseDto(productDetailsDao.save(productDetailsMapper.ProductDetailsCreateRequestDtoToProductDetails(productDetail)));
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
                    ProductDetails productDetails = productDetailsDao.isProductDetailsExists(id, detailName[index]);
                    if (productDetails == null) {
                        ProductDetailsUpdateRequestDto productDetails1 = new ProductDetailsUpdateRequestDto();
                        productDetails1.setProductId(id);
                        productDetails1.setName(detailName[index]);
                        productDetails1.setValue(detailValue[index]);

                        ProductDetails pd = productDetailsDao.save(productDetailsMapper.ProductDetailsUpdateRequestDtoToProductDetails(productDetails1));

                        listProductDetails.add(pd);
                    } else {
                        productDetails.setValue(detailValue[index]);
                        productDetailsDao.save(productDetails);
                        listProductDetails.add(productDetails);
                    }
                } else if (detailName[index].isEmpty()) {
                    throw new InvalidInputDetailsException(ErrorCodes.E2002, Arrays.toString(detailName));
                }
            }
        } else {
            throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(id));
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
            throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(id));
        }
        return productDetailsDao.getByProductId(id)
                .stream()
                .map(productDetailsMapper::productDetailsToProductDetailsFindResponseDto).toList();
    }

    private boolean isProductDetailsExitsWithProductId(UUID productId) {
        if (!productDetailsDao.isProductDetailsExistsWithProductId(productId)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
