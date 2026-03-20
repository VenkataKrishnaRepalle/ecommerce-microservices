package com.pm.spring.ema.product.service.Impl;

import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.dto.ProductDto;
import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.InvalidInputException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.common.util.handler.TokenHandler;
import com.pm.spring.ema.product.entity.ProductImage;
import com.pm.spring.ema.product.feign.BrandClient;
import com.pm.spring.ema.product.mapper.ProductMapper;
import com.pm.spring.ema.product.repository.ProductImageRepository;
import com.pm.spring.ema.product.repository.ProductRepository;
import com.pm.spring.ema.product.service.ProductService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.UUID;

import feign.FeignException;
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

    private final ProductRepository productRepo;

    private final BrandClient brandClient;

    private final TokenHandler tokenHandler;

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
        validateBrand(productDto.getBrandId());

        var discountedPercent = getDiscountedPercent(productDto.getCost(), productDto.getPrice());
        productDto.setDiscountPercent(discountedPercent);
        return productMapper.toProductDto(
                productRepo.save(productMapper.toProduct(productDto)));
    }

    private void validateBrand(UUID brandId) {
        if (brandId == null) {
            throw new InvalidInputException("Invalid input", "Brand Id is missing from input");
        }
        try {
            brandClient.getById(tokenHandler.getToken(), brandId);
        } catch (FeignException ex) {
            if (ex.status() == Integer.parseInt(HttpStatusCodes.NOT_FOUND)) {
                throw new NotFoundException(ErrorCodes.E1502, brandId.toString());
            }
            if (ex.status() == Integer.parseInt(HttpStatusCodes.BAD_REQUEST)) {
                throw new InvalidInputException("Invalid Input", "Invalid category id: " + brandId);
            }
            throw ex;
        }
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
        var product = productRepo.findByName(productName);
        return product != null;
    }

    public boolean isProductExists(final UUID id) {
        var product = productRepo.findById(id);
        return product.isPresent();
    }

    public void uploadImage(final UUID id, final String fileName) {
        var product = productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCodes.E2001, String.valueOf(id)));
        if (product.getMainImage().isEmpty()) {
            product.setMainImage(fileName + "_" + id);
            productRepo.save(product);
        } else {
            ProductImage productImage = new ProductImage();
            productImage.setName(fileName + "_" + id);
            productImage.setProductId(id);
            productImageRepo.save(productImage);
        }
    }

    public ProductDto update(final UUID id, final ProductDto productDto) {
        productRepo.findById(id)
                .orElseThrow(() -> new NotFoundException(ErrorCodes.E2001, String.valueOf(id)));

        validateBrand(productDto.getBrandId());

        var product = productMapper.toProduct(productDto);

        var discountedPercent = getDiscountedPercent(productDto.getCost(), productDto.getPrice());
        product.setId(id);
        product.setDiscountPercent(discountedPercent);

        return productMapper.toProductDto(productRepo.save(product));
    }

    private BigDecimal getDiscountedPercent(BigDecimal cost, BigDecimal price) {
        return (cost.compareTo(BigDecimal.ZERO) > 0 && price.compareTo(BigDecimal.ZERO) > 0
                && cost.compareTo(price) > 0)
                ? cost.subtract(price)
                .multiply(BigDecimal.valueOf(100))
                .divide(cost, 2, RoundingMode.HALF_UP)
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

    @Override
    public List<ProductDto> getByBrandId(UUID brandId) {
        var productList = productRepo.getByBrandId(brandId);
        if (productList.isEmpty()) {
            throw new NotFoundException(ErrorCodes.E2001, String.valueOf(brandId));
        }

        return productList.stream().map(productMapper::toProductDto).toList();
    }

    @Override
    public Boolean isProductExistsById(UUID productId) {
        var product = productRepo.findById(productId);
        if (product.isPresent() && product.get().getIsEnabled()) {
            if (product.get().getInStock()) {
                return Boolean.TRUE;
            } else {
                throw new NotFoundException(ErrorCodes.E2003, productId.toString());
            }
        }
        return Boolean.FALSE;
    }
}
