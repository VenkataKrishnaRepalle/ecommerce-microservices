package com.pm.spring.ema.service.Impl;

import com.pm.spring.ema.common.util.dto.ProductFindResponseDto;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.dto.ProductCreateRequestDto;
import com.pm.spring.ema.dto.ProductCreateResponseDto;
import com.pm.spring.ema.dto.ProductImageCreateRequestDto;
import com.pm.spring.ema.dto.ProductUpdateRequestDto;
import com.pm.spring.ema.dto.ProductUpdateResponseDto;
import com.pm.spring.ema.model.dao.Impl.ProductImageDaoImpl;
import com.pm.spring.ema.model.dao.ProductDao;
import com.pm.spring.ema.model.entity.Product;
import com.pm.spring.ema.exception.ProductAlreadyPresentException;
import com.pm.spring.ema.exception.ProductNotFoundException;
import com.pm.spring.ema.dto.mapper.ProductImageMapper;
import com.pm.spring.ema.dto.mapper.ProductMapper;
import com.pm.spring.ema.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private static final int PRODUCT_PER_PAGE = 5;


    private final ProductImageDaoImpl productImageDao;

    private final ProductMapper productMapper;

    private final ProductImageMapper productImageMapper;

    private final ProductDao productDao;

    public List<ProductFindResponseDto> listAll() {
        return productDao.findAll()
                .stream()
                .map(productMapper::productToProductFindResponseDto).toList();
    }

    public ProductFindResponseDto getProductById(final UUID id) throws ProductNotFoundException {
        var product = productDao.findById(id);

        if (product.isPresent()) {
            return productMapper.productToProductFindResponseDto(product.get());
        }
        throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(id));

    }

    public ProductCreateResponseDto create(final ProductCreateRequestDto productCreateRequestDto) {
        if (isProductNameExists(productCreateRequestDto.getName())) {
            throw new ProductAlreadyPresentException(ErrorCodes.E2002, productCreateRequestDto.getName());
        }
        var cost = productCreateRequestDto.getCost();
        var price = productCreateRequestDto.getPrice();
        var discountedPercent = (cost.compareTo(BigDecimal.ZERO) > 0 && price.compareTo(BigDecimal.ZERO) > 0 && cost.compareTo(price) > 0) ? (((cost.subtract(price).divide(cost))).multiply(BigDecimal.valueOf(100))) : BigDecimal.ZERO;

        productCreateRequestDto.setDiscountPercent(discountedPercent);

        return productMapper.productToProductCreateResponseDto(
                productDao.save(productMapper.productCreateRequestDtoToProduct(productCreateRequestDto)));
    }

    public void updateProductStatusById(final UUID id, final boolean status) {
        var product = productDao.findById(id);
        if (product.isEmpty()) {
            throw new ProductNotFoundException(ErrorCodes.E2001, product.get().getId().toString());
        }
        productDao.updateEnabledStatus(status, id);
    }

    public void deleteProductById(final UUID id) {
        var countById = productDao.countById(id);

        if (countById == 0) {
            throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(id));
        }
        productDao.deleteById(id);
    }

    public boolean isProductNameExists(final String productName) {
        var optionalProduct = productDao.findByName(productName);

        if (optionalProduct != null) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;

    }

    public boolean isProductExists(final UUID id) {
        var optionalProduct = productDao.findById(id);

        if (optionalProduct.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    public void uploadImage(final UUID id, final String fileName) {
        var optionalProduct = productDao.findById(id);

        if (optionalProduct.isEmpty()) {
            throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(id));
        }
        Product product = optionalProduct.get();
        if (product.getMainImage().isEmpty()) {
            product.setMainImage(fileName + "_" + id);
            productDao.save(product);
        } else {
            ProductImageCreateRequestDto productImageCreateRequestDto = new ProductImageCreateRequestDto();
            productImageCreateRequestDto.setName(fileName + "_" + id);
            productImageCreateRequestDto.setProductId(id);
            productImageDao.save(productImageMapper.ProductImageCreateRequestDtoToProductImage(
                    productImageCreateRequestDto));
        }
    }

    public ProductUpdateResponseDto update(final UUID id, final ProductUpdateRequestDto productUpdateRequestDto) {
        var existingProduct = productDao.findById(id);

        if (existingProduct.isEmpty()) {
            throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(id));
        }

        var product = productMapper.productUpdateRequestDtoToProduct(productUpdateRequestDto);

        var cost = productUpdateRequestDto.getCost();
        var price = productUpdateRequestDto.getPrice();
        var discountedPercent = (cost.compareTo(BigDecimal.ZERO) > 0 && price.compareTo(BigDecimal.ZERO) > 0 && cost.compareTo(price) > 0) ? (((cost.subtract(price).divide(cost))).multiply(BigDecimal.valueOf(100))) : BigDecimal.ZERO;

        product.setId(id);
        product.setDiscountPercent(discountedPercent);

        return productMapper.productToProductUpdateResponseDto(productDao.save(product));
    }

    public List<ProductFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        var sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        var pageable = PageRequest.of(pageNumber - 1, PRODUCT_PER_PAGE, sort);

        if (keyword != null) {
            return productDao.findAll(keyword, pageable)
                    .stream()
                    .map(productMapper::productToProductFindResponseDto)
                    .toList();
        }
        return productDao.findAll()
                .stream()
                .map(productMapper::productToProductFindResponseDto)
                .toList();
    }

    public List<ProductFindResponseDto> getByCategoryId(UUID categoryId) {
        var productList = productDao.getByCategoryId(categoryId);
        if (productList.isEmpty()) {
            throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(categoryId));
        }
        return productList
                .stream()
                .map(productMapper::productToProductFindResponseDto)
                .toList();
    }

    @Override
    public List<ProductFindResponseDto> getByBrandId(UUID brandId) {
        var productList = productDao.getByBrandId(brandId);
        if (productList.isEmpty()) {
            throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(brandId));
        }

        return productList
                .stream()
                .map(productMapper::productToProductFindResponseDto)
                .toList();
    }

    @Override
    public List<ProductFindResponseDto> getByCategoryIdAndBrandId(UUID categoryId, UUID brandId) {
        if (productDao.getByCategoryId(categoryId).isEmpty()) {
            throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(categoryId));
        } else if (productDao.getByBrandId(brandId).isEmpty()) {
            throw new ProductNotFoundException(ErrorCodes.E2001, String.valueOf(brandId));
        }
        return productDao.getByCategoryIdAndBrandId(categoryId, brandId)
                .stream()
                .map(productMapper::productToProductFindResponseDto)
                .toList();
    }

    @Override
    public Boolean isProductExistsById(UUID productId) {
        var product = productDao.findById(productId);

        var isProductExists = isProductExists(productId);
        if (Boolean.TRUE.equals(isProductExists) && Boolean.TRUE.equals(product.get().getIsEnabled())) {
            if (Boolean.TRUE.equals(product.get().getInStock())) {
                return Boolean.TRUE;
            } else {
                throw new ProductNotFoundException(ErrorCodes.E2003, productId.toString());
            }
        }
        return Boolean.FALSE;
    }

}
