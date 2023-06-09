package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ProductDetailsFindResponseDto;
import com.spring6.ecommerce.common.dto.ProductFindResponseDto;
import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.entity.Product;
import com.spring6.ecommerce.entity.ProductDetails;
import com.spring6.ecommerce.exception.ProductImageAlreaydFoundException;
import com.spring6.ecommerce.exception.ProductNotFoundException;
import com.spring6.ecommerce.mapper.ProductDetailsMapper;
import com.spring6.ecommerce.mapper.ProductImageMapper;
import com.spring6.ecommerce.mapper.ProductMapper;
import com.spring6.ecommerce.repository.ProductDetailsRepository;
import com.spring6.ecommerce.repository.ProductImageRepository;
import com.spring6.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final ProductDetailsRepository productDetailsRepository;

    private final ProductMapper productMapper;

    private final ProductImageMapper productImageMapper;

    private final ProductDetailsMapper productDetailsMapper;

    public List<ProductFindResponseDto> listAll() {
        System.out.println(productRepository.findAll().get(0));
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductFindResponseDto).toList();
    }

    @Override
    public ProductFindResponseDto getProductById(final UUID productId) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productId);

        if (product.isPresent()) {
            return productMapper.productToProductFindResponseDto(product.get());
        }

        throw new ProductNotFoundException("Could not find any Product with Id " + productId);
    }

    @Override
    public ProductCreateResponseDto addProduct(final ProductCreateRequestDto productCreateRequestDto) {
        Float cost = productCreateRequestDto.getCost();
        Float price = productCreateRequestDto.getPrice();
        Float discountedPercent = ((cost - price) / cost) * 100;

        productCreateRequestDto.setDiscountPercent(discountedPercent);

        return productMapper.productToProductCreateResponseDto(
                productRepository.save(productMapper.productCreateRequestDtoToProduct(productCreateRequestDto)));
    }

    @Override
    public void updateProductEnabledStatus(final UUID productId, final boolean status) {
        productRepository.updateEnabledStatus(status, productId);
    }

    @Override
    public void deleteProductById(final UUID productId) {
        Long countById = productRepository.countById(productId);

        if (countById == null || countById == 0) {
            throw new ProductNotFoundException("couldn't find any product with id " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public boolean isProductNameExists(final String productName) {
        Product OptionalProduct = productRepository.findByName(productName);

        if (OptionalProduct != null) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

    @Override
    public boolean isProductExists(final UUID productId) {
        Optional<Product> optionalProduct = Optional.of(productRepository.getById(productId));

        if (optionalProduct.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public void updateImageName(final UUID productId, final String fileName) {
        Optional<Product> optionalProduct = Optional.of(productRepository.getById(productId));
        Product product = optionalProduct.get();

        if (!optionalProduct.isPresent()) {
            throw new ProductNotFoundException("could not find the product with id " + productId);
        }

        boolean isProductImageExists = isProductImageExists(fileName);
        boolean isProductMainImageExists = isProductMainImageExists(fileName);

        if (isProductImageExists == true || isProductMainImageExists == true) {
            throw new ProductImageAlreaydFoundException("Already Image Exists with Name " + fileName);
        }

        if (product.getMainImage().isEmpty()) {
            product.setMainImage(fileName);
            productRepository.save(product);
        } else {
            ProductImageCreateRequestDto productImageCreateRequestDto = new ProductImageCreateRequestDto();
            productImageCreateRequestDto.setName(fileName);
            productImageCreateRequestDto.setProductId(productId);
            productImageRepository.save(productImageMapper.ProductImageCreateRequestDtoToProductImage(productImageCreateRequestDto));
        }
    }

    public List<ProductDetailsFindResponseDto> addProductDetails(final UUID productId, final String[] detailNames, final String[] detailValues) {
        List<ProductDetailsCreateRequestDto> productDetails = new ArrayList<>();

        for (int count = 0; count < detailNames.length; count++) {
            if (!detailNames[count].isEmpty() && !detailValues[count].isEmpty()) {
                ProductDetailsCreateRequestDto productDetailsRequestDto = new ProductDetailsCreateRequestDto();
                productDetailsRequestDto.setName(detailNames[count]);
                productDetailsRequestDto.setValue(detailValues[count]);
                productDetailsRequestDto.setProductId(productId);

                productDetails.add(productDetailsRequestDto);
            }
        }
        List<ProductDetailsFindResponseDto> productDetailsFindResponseDtoList = new ArrayList<>();

        for (int count = 0; count < productDetails.size(); count++) {

//            ProductDetailsFindResponseDto productDetailsFindResponseDto = isProductDetailsExists(productDetails.get(count).getProductId(), productDetails.get(count).getName(), productDetails.get(count).getValue());
//
//            if(productDetailsFindResponseDto.equals(productDetails.get(count))){
//                throw new RuntimeException("Product with same details already present ProductId: " + productId+ " , Details Name : " + productDetailsFindResponseDto.getName() + " , Details Value : " +productDetailsFindResponseDto.getValue());
//            }

            ProductDetailsFindResponseDto finalProductDetails = productDetailsMapper.productDetailsToProductDetailsFindResponseDto(productDetailsRepository.save(productDetailsMapper.ProductDetailsCreateRequestDtoToProductDetails(productDetails.get(count))));
            productDetailsFindResponseDtoList.add(finalProductDetails);
        }
        return productDetailsFindResponseDtoList;
    }

    @Override
    public ProductUpdateResponseDto updateProduct(final UUID productId,final ProductUpdateRequestDto productUpdateRequestDto) {
        Optional<Product> existingProduct = Optional.of(productRepository.getById(productId));

        if(!existingProduct.isPresent()) {
            throw new ProductNotFoundException("Product with id is not present " + productId);
        }

        Product product = productMapper.productUpdateRequestDtoToProduct(productUpdateRequestDto);

        Float cost = productUpdateRequestDto.getCost();
        Float price = productUpdateRequestDto.getPrice();
        Float discountedPercent = ((cost - price) / cost) * 100;

        product.setId(productId);
        product.setDiscountPercent(discountedPercent);

        return productMapper.productToProductUpdateResponseDto(productRepository.save(product));
    }

//    public ProductDetailsFindResponseDto isProductDetailsExists(UUID productId, String detailName, String detailValue) {
//        ProductDetailsFindResponseDto productDetailsFindResponseDto = productDetailsMapper.productDetailsToProductDetailsFindResponseDto(
//                productDetailsRepository.isProductDetailsExists(productId, detailName, detailValue));
//        System.out.println(productDetailsFindResponseDto);
//        return productDetailsFindResponseDto;
//
//    }


    public boolean isProductImageExists(final String fileName) {
        if (productImageRepository.isProductImageNameExists(fileName) == false) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public boolean isProductMainImageExists(final String fileName) {
        if (productRepository.isProductMainImageExists(fileName) == false) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
