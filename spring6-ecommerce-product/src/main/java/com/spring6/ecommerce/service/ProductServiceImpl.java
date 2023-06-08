package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ProductFindResponseDto;
import com.spring6.ecommerce.dto.ProductCreateRequestDto;
import com.spring6.ecommerce.dto.ProductCreateResponseDto;
import com.spring6.ecommerce.dto.ProductImageCreateRequestDto;
import com.spring6.ecommerce.entity.Product;
import com.spring6.ecommerce.exception.ProductImageAlreaydFoundException;
import com.spring6.ecommerce.exception.ProductNotFoundException;
import com.spring6.ecommerce.mapper.ProductImageMapper;
import com.spring6.ecommerce.mapper.ProductMapper;
import com.spring6.ecommerce.repository.ProductImageRepository;
import com.spring6.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final ProductMapper productMapper;

    private final ProductImageMapper productImageMapper;

    public List<ProductFindResponseDto> listAll() {
        System.out.println(productRepository.findAll().get(0));
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductFindResponseDto).toList();
    }

    @Override
    public ProductFindResponseDto getProductById(UUID productId) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(productId);

        if(product.isPresent()) {
            return productMapper.productToProductFindResponseDto(product.get());
        }

        throw new ProductNotFoundException("Could not find any Product with Id " +productId);
    }

    @Override
    public ProductCreateResponseDto addProduct(ProductCreateRequestDto productCreateRequestDto) {
        Float cost = productCreateRequestDto.getCost();
        Float price = productCreateRequestDto.getPrice();
        Float discountedPercent = ((cost-price)/cost)*100;

        productCreateRequestDto.setDiscountPercent(discountedPercent);

        return productMapper.productToProductCreateResponseDto(
                productRepository.save(productMapper.productCreateRequestDtoToProduct(productCreateRequestDto)));
    }

    @Override
    public void updateProductEnabledStatus(UUID productId, boolean status) {
        productRepository.updateEnabledStatus(status, productId);
    }

    @Override
    public void deleteProductById(UUID productId) {
        Long countById = productRepository.countById(productId);

        if(countById == null || countById == 0) {
            throw new ProductNotFoundException("couldn't find any product with id " + productId);
        }
        productRepository.deleteById(productId);
    }

    @Override
    public boolean isProductNameExists(String productName) {
        Product OptionalProduct = productRepository.findByName(productName);

        if(OptionalProduct != null) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

    @Override
    public boolean isProductExists(UUID productId) {
        Optional<Product> optionalProduct= Optional.of(productRepository.getById(productId));

        if(optionalProduct.isPresent())
        {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public void updateImageName(UUID productId, String fileName) {
        Optional<Product> optionalProduct= Optional.of(productRepository.getById(productId));
        Product product = optionalProduct.get();

        if(!optionalProduct.isPresent())
        {
            throw new ProductNotFoundException("could not find the product with id " +productId);
        }

        boolean isProductImageExists = isProductImageExists(fileName);
        boolean isProductMainImageExists = isProductMainImageExists(fileName);

        if (isProductImageExists == true || isProductMainImageExists == true) {
            throw new ProductImageAlreaydFoundException("Already Image Exists with Name " + fileName);
        }

        if(product.getMainImage().isEmpty()) {
            product.setMainImage(fileName);
            productRepository.save(product);
        }
        else {
            ProductImageCreateRequestDto productImageCreateRequestDto = new ProductImageCreateRequestDto();
            productImageCreateRequestDto.setName(fileName);
            productImageCreateRequestDto.setProductId(productId);
            productImageRepository.save(productImageMapper.ProductImageCreateRequestDtoToProductImage(productImageCreateRequestDto));
        }
    }

    public boolean isProductImageExists(String fileName)
    {
        if(productImageRepository.isProductImageNameExists(fileName) == false) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    public boolean isProductMainImageExists(String fileName) {
        if(productRepository.isProductMainImageExists(fileName) == false) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }
}
