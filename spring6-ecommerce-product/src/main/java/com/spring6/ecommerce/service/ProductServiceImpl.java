package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commonutil.dto.ProductFindResponseDto;
import com.spring6.ecommerce.dto.ProductCreateRequestDto;
import com.spring6.ecommerce.dto.ProductCreateResponseDto;
import com.spring6.ecommerce.entity.Product;
import com.spring6.ecommerce.exception.ProductNotFoundException;
import com.spring6.ecommerce.mapper.ProductMapper;
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

    private final ProductMapper productMapper;

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


}
