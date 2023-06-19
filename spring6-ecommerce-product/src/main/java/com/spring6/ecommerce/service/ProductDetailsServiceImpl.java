package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ProductDetailsFindResponseDto;
import com.spring6.ecommerce.dto.ProductDetailsCreateRequestDto;
import com.spring6.ecommerce.dto.ProductDetailsUpdateRequestDto;
import com.spring6.ecommerce.entity.ProductDetails;
import com.spring6.ecommerce.exception.InvalidInputDetailsException;
import com.spring6.ecommerce.exception.ProductAlreadyPresentException;
import com.spring6.ecommerce.exception.ProductNotFoundException;
import com.spring6.ecommerce.mapper.ProductDetailsMapper;
import com.spring6.ecommerce.repository.ProductDetailsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
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
                if(isProductDetailsExists != null){
                    throw new ProductAlreadyPresentException("Product Details of name "+ detailNames[count] + " with product id " + id + " is Already Exists");
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

        if(isProductDetailsExists) {
            for(int index=0; index < detailName.length ; index++) {
                if(!detailName[index].isEmpty() && !detailValue[index].isEmpty()) {
                    ProductDetails productDetails = productDetailsRepository.isProductDetailsExists(id, detailName[index]);
                    if(productDetails == null) {
                        ProductDetailsUpdateRequestDto productDetails1 = new ProductDetailsUpdateRequestDto();
                        productDetails1.setProductId(id);
                        productDetails1.setName(detailName[index]);
                        productDetails1.setValue(detailValue[index]);

                        ProductDetails pd = productDetailsRepository.save(productDetailsMapper.ProductDetailsUpdateRequestDtoToProductDetails(productDetails1));

                        listProductDetails.add(pd);
                    }
                    else {
                        productDetails.setValue(detailValue[index]);
                        productDetailsRepository.save(productDetails);
                        listProductDetails.add(productDetails);
                    }
                }
                else {
                    throw new InvalidInputDetailsException("Invalid Input Details");
                }
            }
        }
        else {
            throw new ProductNotFoundException("Couldn't found the product with Id: "+id);
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
        if(!isProductDetailsExitsWithProductId(id)){
            throw new ProductNotFoundException("Could not found ProductDetails with product id: "+ id);
        }
        return productDetailsRepository.getByProductId(id)
                .stream()
                .map(productDetailsMapper::productDetailsToProductDetailsFindResponseDto).toList();
    }

    public boolean isProductDetailsExitsWithProductId(UUID productId) {
        if(!productDetailsRepository.isProductDetailsExistsWithProductId(productId)) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

}
