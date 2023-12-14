package com.pm.spring.ema.ecommerce.service;

import com.pm.spring.ema.common.dto.ProductFindResponseDto;
import com.pm.spring.ema.ecommerce.dto.ProductCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductCreateResponseDto;
import com.pm.spring.ema.ecommerce.dto.ProductImageCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductUpdateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductUpdateResponseDto;
import com.pm.spring.ema.ecommerce.entity.Product;
import com.pm.spring.ema.ecommerce.exception.ErrorCode;
import com.pm.spring.ema.ecommerce.exception.ProductAlreadyPresentException;
import com.pm.spring.ema.ecommerce.exception.ProductNotFoundException;
import com.pm.spring.ema.ecommerce.mapper.ProductImageMapper;
import com.pm.spring.ema.ecommerce.mapper.ProductMapper;
import com.pm.spring.ema.ecommerce.repository.ProductImageRepository;
import com.pm.spring.ema.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final int PRODUCT_PER_PAGE = 5;

    private final ProductRepository productRepository;

    private final ProductImageRepository productImageRepository;

    private final ProductMapper productMapper;

    private final ProductImageMapper productImageMapper;
    public List<ProductFindResponseDto> listAll() {
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductFindResponseDto).toList();
    }

    public ProductFindResponseDto getProductById(final UUID id) throws ProductNotFoundException {
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            return productMapper.productToProductFindResponseDto(product.get());
        }
        throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(id));

}

    public ProductCreateResponseDto create(final ProductCreateRequestDto productCreateRequestDto) {
        if(isProductNameExists(productCreateRequestDto.getName())) {
            throw new ProductAlreadyPresentException(ErrorCode.E1502.getCode(), productCreateRequestDto.getName());
        }
        Float cost = productCreateRequestDto.getCost();
        Float price = productCreateRequestDto.getPrice();
        Float discountedPercent = null;
        if (cost > 0 && price > 0 && cost > price)  discountedPercent = ((cost - price) / cost) * 100; else  discountedPercent = 0.0f;
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        discountedPercent = Float.valueOf(decimalFormat.format(discountedPercent));

        productCreateRequestDto.setDiscountPercent(discountedPercent);

        return productMapper.productToProductCreateResponseDto(
                productRepository.save(productMapper.productCreateRequestDtoToProduct(productCreateRequestDto)));
    }
    
    public void updateProductStatusById(final UUID id, final boolean status) {
        productRepository.updateEnabledStatus(status, id);
    }
    
    public void deleteProductById(final UUID id) {
        long countById = productRepository.countById(id);

        if (countById == 0) {
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(id));
    }
        productRepository.deleteById(id);
    }

    public boolean isProductNameExists(final String productName) {
        Product optionalProduct = productRepository.findByName(productName);

        if (optionalProduct != null) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

    public boolean isProductExists(final UUID id) {
        Optional<Product> optionalProduct = Optional.of(productRepository.getReferenceById(id));

        if (optionalProduct.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public void uploadImage(final UUID id, final String fileName) {
        Optional<Product> optionalProduct = Optional.of(productRepository.getReferenceById(id));
        Product product = optionalProduct.get();

        if (!optionalProduct.isPresent()) {
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(id));
        }

        if (product.getMainImage().isEmpty()) {
            product.setMainImage(fileName+"_"+id);
            productRepository.save(product);
        } else {
            ProductImageCreateRequestDto productImageCreateRequestDto = new ProductImageCreateRequestDto();
            productImageCreateRequestDto.setName(fileName+"_"+id);
            productImageCreateRequestDto.setProductId(id);
            productImageRepository.save(productImageMapper.ProductImageCreateRequestDtoToProductImage(
                    productImageCreateRequestDto));
        }
    }
    
    public ProductUpdateResponseDto update(final UUID id, final ProductUpdateRequestDto productUpdateRequestDto) {
        Optional<Product> existingProduct = Optional.of(productRepository.getReferenceById(id));

        if(existingProduct.isEmpty()) {
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(id));
        }

        Product product = productMapper.productUpdateRequestDtoToProduct(productUpdateRequestDto);

        Float cost = productUpdateRequestDto.getCost();
        Float price = productUpdateRequestDto.getPrice();
        Float discountedPercent = ((cost - price) / cost) * 100;

        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        discountedPercent = Float.valueOf(decimalFormat.format(discountedPercent));

        product.setId(id);
        product.setDiscountPercent(discountedPercent);

        return productMapper.productToProductUpdateResponseDto(productRepository.save(product));
    }
    
    public List<ProductFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, PRODUCT_PER_PAGE, sort);

        if(keyword != null) {
            return productRepository.findAll(keyword, pageable)
                    .stream()
                    .map(productMapper::productToProductFindResponseDto)
                    .toList();
        }
        return productRepository.findAll()
                .stream()
                .map(productMapper::productToProductFindResponseDto)
                .toList();
    }

    public List<ProductFindResponseDto> getByCategoryId(UUID categoryId) {
        if(productRepository.getByCategoryId(categoryId).isEmpty()){
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(categoryId));
        }
        return productRepository.getByCategoryId(categoryId)
                .stream()
                .map(productMapper::productToProductFindResponseDto)
                .toList();
    }

    @Override
    public List<ProductFindResponseDto> getByBrandId(UUID brandId) {
        if(productRepository.getByBrandId(brandId).isEmpty()) {
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(brandId));
        }

        return productRepository.getByBrandId(brandId)
                .stream()
                .map(productMapper::productToProductFindResponseDto)
                .toList();
    }

    @Override
    public List<ProductFindResponseDto> getByCategoryIdAndBrandId(UUID categoryId, UUID brandId) {
        if(productRepository.getByCategoryId(categoryId).isEmpty()) {
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(categoryId));
        } else if (productRepository.getByBrandId(brandId).isEmpty()) {
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(brandId));
        }
        return productRepository.getByCategoryIdAndBrandId(categoryId, brandId)
                .stream()
                .map(productMapper::productToProductFindResponseDto)
                .toList();
    }

}
