package com.pm.spring.ema.product.service.Impl;

import com.pm.spring.ema.common.util.dto.ProductDto;
import com.pm.spring.ema.common.util.dto.ProductImageDto;
import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.product.mapper.ProductImageMapper;
import com.pm.spring.ema.product.mapper.ProductMapper;
import com.pm.spring.ema.product.model.entity.Product;
import com.pm.spring.ema.product.model.repository.ProductImageRepository;
import com.pm.spring.ema.product.model.repository.ProductRepository;
import com.pm.spring.ema.product.service.ProductService;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final int PRODUCT_PER_PAGE = 5;

    private final ProductImageRepository productImageRepo;

    private final ProductMapper productMapper;

    private final ProductImageMapper productImageMapper;

    private final ProductRepository productRepo;

    public List<ProductDto> listAll() {
        return productRepo.findAll().stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    public ProductDto getProductById(final UUID id) {
        var product = productRepo.findById(id);

        if (product.isPresent()) {
            return productMapper.toProductDto(product.get());
        }
        throw new NotFoundException(ErrorCodes.E2001, String.valueOf(id));
    }

    public ProductDto create(final ProductDto productDto) {
        if (isProductNameExists(productDto.getName())) {
            throw new FoundException(ErrorCodes.E2002, productDto.getName());
        }
        var discountedPercent =getDiscountedPercent(productDto.getCost(), productDto.getPrice());
        productDto.setDiscountPercent(discountedPercent);

        return productMapper.toProductDto(
                productRepo.save(productMapper.toProduct(productDto)));
    }

    public void updateProductStatusById(final UUID id, final boolean status) {
        var product = productRepo.findById(id);
        if (product.isEmpty()) {
            throw new NotFoundException(ErrorCodes.E2001, id.toString());
        }
        productRepo.updateEnabledStatus(status, id);
    }

    public void deleteProductById(final UUID id) {
        var countById = productRepo.countById(id);

        if (countById == 0) {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(id));
        }
        productRepo.deleteById(id);
    }

    public boolean isProductNameExists(final String productName) {
        var optionalProduct = productRepo.findByName(productName);

        if (optionalProduct != null) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public boolean isProductExists(final UUID id) {
        var optionalProduct = productRepo.findById(id);

        if (optionalProduct.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public void uploadImage(final UUID id, final String fileName) {
        var optionalProduct = productRepo.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(id));
        }
        Product product = optionalProduct.get();
        if (product.getMainImage().isEmpty()) {
            product.setMainImage(fileName + "_" + id);
            productRepo.save(product);
        } else {
            ProductImageDto productImageCreateRequestDto = new ProductImageDto();
            productImageCreateRequestDto.setName(fileName + "_" + id);
            productImageCreateRequestDto.setProductId(id);
            productImageRepo.save(productImageMapper.toProductImage(productImageCreateRequestDto));
        }
    }

    public ProductDto update(final UUID id, final ProductDto productDto) {
        var existingProduct = productRepo.findById(id);

        if (existingProduct.isEmpty()) {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(id));
        }

        var product = productMapper.toProduct(productDto);

        var discountedPercent = getDiscountedPercent(productDto.getCost(), productDto.getPrice());
        product.setId(id);
        product.setDiscountPercent(discountedPercent);

        return productMapper.toProductDto(productRepo.save(product));
    }

    private BigDecimal getDiscountedPercent(BigDecimal cost, BigDecimal price) {
        return (cost.compareTo(BigDecimal.ZERO) > 0
                && price.compareTo(BigDecimal.ZERO) > 0
                && cost.compareTo(price) > 0)
                ? (((cost.subtract(price).divide(cost))).multiply(BigDecimal.valueOf(100)))
                : BigDecimal.ZERO;
    }

    public List<ProductDto> findByPage(
            int pageNumber, String sortField, String sortDir, String keyword) {
        var sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        var pageable = PageRequest.of(pageNumber - 1, PRODUCT_PER_PAGE, sort);

        if (keyword != null) {
            return productRepo.findAll(keyword, pageable).stream()
                    .map(productMapper::toProductDto)
                    .toList();
        }
        return productRepo.findAll().stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    public List<ProductDto> getByCategoryId(UUID categoryId) {
        var productList = productRepo.getByCategoryId(categoryId);
        if (productList.isEmpty()) {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(categoryId));
        }
        return productList.stream().map(productMapper::toProductDto).toList();
    }

    @Override
    public List<ProductDto> getByBrandId(UUID brandId) {
        var productList = productRepo.getByBrandId(brandId);
        if (productList.isEmpty()) {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(brandId));
        }

        return productList.stream().map(productMapper::toProductDto).toList();
    }

    @Override
    public List<ProductDto> getByCategoryIdAndBrandId(UUID categoryId, UUID brandId) {
        if (productRepo.getByCategoryId(categoryId).isEmpty()) {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(categoryId));
        } else if (productRepo.getByBrandId(brandId).isEmpty()) {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(brandId));
        }
        return productRepo.getByCategoryIdAndBrandId(categoryId, brandId).stream()
                .map(productMapper::toProductDto)
                .toList();
    }

    @Override
    public Boolean isProductExistsById(UUID productId) {
        var product = productRepo.findById(productId);
        if (product.isPresent() && product.get().getIsEnabled()) {
            if (Boolean.TRUE.equals(product.get().getInStock())) {
                return Boolean.TRUE;
            } else {
                throw new NotFoundException(ErrorCodes.E2003, productId.toString());
            }
        }
        return Boolean.FALSE;
    }
}
