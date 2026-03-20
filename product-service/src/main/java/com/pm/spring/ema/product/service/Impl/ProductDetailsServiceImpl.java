package com.pm.spring.ema.product.service.Impl;

import com.pm.spring.ema.common.util.dto.ProductDetailsDto;
import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.InvalidInputException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.product.mapper.ProductDetailsMapper;
import com.pm.spring.ema.product.entity.ProductDetails;
import com.pm.spring.ema.product.repository.ProductDetailsRepository;
import com.pm.spring.ema.product.service.ProductDetailsService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductDetailsServiceImpl implements ProductDetailsService {

    private final ProductDetailsRepository productDetailsDao;

    private final ProductDetailsMapper productDetailsMapper;


    public List<ProductDetailsDto> create(
            final UUID productId, final String[] detailNames, final String[] detailValues) {
        List<ProductDetailsDto> productDetails = new ArrayList<>();

        for (int count = 0; count < detailNames.length; count++) {
            if (!detailNames[count].isEmpty() && !detailValues[count].isEmpty()) {
                ProductDetailsDto isProductDetailsExists =
                        productDetailsMapper.toProductDetailsDto(
                                productDetailsDao.isProductDetailsExists(productId, detailNames[count]));
                if (isProductDetailsExists != null) {
                    throw new FoundException(ErrorCodes.E2002, detailNames[count]);
                }
                ProductDetailsDto productDetailsDto = ProductDetailsDto.builder()
                        .name(detailNames[count])
                        .value(detailValues[count])
                        .productId(productId)
                        .build();
                productDetails.add(productDetailsDto);
            }
        }
        List<ProductDetailsDto> productDetailsDtoList = new ArrayList<>();
        for (ProductDetailsDto productDetail : productDetails) {
            ProductDetailsDto finalProductDetails =
                    productDetailsMapper.toProductDetailsDto(
                            productDetailsDao.save(productDetailsMapper.toProductDetails(productDetail)));
            productDetailsDtoList.add(finalProductDetails);
        }
        return productDetailsDtoList;
    }

    public List<ProductDetailsDto> update(
            UUID productId, String[] detailName, String[] detailValue) {

        List<ProductDetails> listProductDetails = new ArrayList<>();

        boolean isProductDetailsExists = isProductDetailsExitsWithProductId(productId);

        if (isProductDetailsExists) {
            for (int index = 0; index < detailName.length; index++) {
                if (!detailName[index].isEmpty() && !detailValue[index].isEmpty()) {
                    ProductDetails productDetails =
                            productDetailsDao.isProductDetailsExists(productId, detailName[index]);
                    if (productDetails == null) {
                        ProductDetailsDto productDetailsDto = ProductDetailsDto.builder()
                                .name(detailName[index])
                                .value(detailValue[index])
                                .productId(productId)
                                .build();

                        ProductDetails pd = productDetailsDao.save(productDetailsMapper.toProductDetails(productDetailsDto));
                        listProductDetails.add(pd);
                    } else {
                        productDetails.setValue(detailValue[index]);
                        productDetailsDao.save(productDetails);
                        listProductDetails.add(productDetails);
                    }
                } else if (detailName[index].isEmpty()) {
                    throw new InvalidInputException(ErrorCodes.E2002, Arrays.toString(detailName));
                }
            }
        } else {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(productId));
        }

        List<ProductDetailsDto> productDetailsDtoList = new ArrayList<>();

        for (ProductDetails listProductDetail : listProductDetails) {
            ProductDetailsDto finalProductDetails =
                    productDetailsMapper.toProductDetailsDto(listProductDetail);
            productDetailsDtoList.add(finalProductDetails);
        }
        return productDetailsDtoList;
    }

    @Override
    public List<ProductDetailsDto> getByProductId(UUID id) {
        if (!isProductDetailsExitsWithProductId(id)) {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(id));
        }
        return productDetailsDao.getByProductId(id).stream()
                .map(productDetailsMapper::toProductDetailsDto)
                .toList();
    }

    private boolean isProductDetailsExitsWithProductId(UUID productId) {
        if (!productDetailsDao.isProductDetailsExistsWithProductId(productId)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
