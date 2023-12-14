package com.pm.spring.ema.ecommerce.controller;

import com.pm.spring.ema.common.dto.ProductFindResponseDto;
import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.ecommerce.dto.ProductCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductCreateResponseDto;
import com.pm.spring.ema.ecommerce.dto.ProductUpdateRequestDto;
import com.pm.spring.ema.ecommerce.dto.ProductUpdateResponseDto;
import com.pm.spring.ema.ecommerce.exception.ErrorCode;
import com.pm.spring.ema.ecommerce.exception.ProductNotFoundException;
import com.pm.spring.ema.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(
            summary = "Get List of Products",
            description = "List of Products",
            tags = "Product")
    @GetMapping("list")
    public ResponseEntity<List<ProductFindResponseDto>> findAll() {
        return new ResponseEntity<>(productService.listAll(), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Product by uuid",
            description = "Get Product by uuid",
            tags = "Product")
    @GetMapping("{id}")
    public ResponseEntity<ProductFindResponseDto> findById(@PathVariable final UUID id) {
        return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
    }

    @Operation(
            summary = "Insert Product",
            description = "Add Product",
            tags = "Product")
    @PostMapping(value = "create")
    public ResponseEntity<HttpHeaders> create(@RequestBody @Valid final ProductCreateRequestDto productCreateRequestDto) {
        ProductCreateResponseDto savedProduct = productService.create(productCreateRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/brand" + savedProduct.getId());
        return new ResponseEntity(savedProduct, httpHeaders, HttpStatus.CREATED);
    }

    @Operation(
            summary = "Insert Product Images",
            description = "Add Product Image",
            tags = "Product Image")
    @PostMapping(value = "upload-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> uploadImage(@PathVariable @NotNull final UUID id,
                                                      @NotNull @RequestParam(name = "fileImage", value = "fileImage") final MultipartFile multipartFile)
            throws IOException, ProductNotFoundException {
        if (!productService.isProductExists(id)) {
            throw new ProductNotFoundException(ErrorCode.E1501.getCode(), String.valueOf(id));
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uploadDir = "spring6-ecommerce-product/product-images";
            FileUploadUtils.saveFile(uploadDir, fileName+"_"+id, multipartFile.getInputStream());

            productService.uploadImage(id, fileName);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(
            summary = "Update Product",
            description = "Update Product",
            tags = "Product")
    @PutMapping("update/{id}")
    public ResponseEntity<ProductUpdateResponseDto> update(@PathVariable UUID id,
                                                           @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        return new ResponseEntity<>(productService.update(id, productUpdateRequestDto), HttpStatus.ACCEPTED);
    }


    @Operation(
            summary = "Update existing Product Status by uuid",
            description = "Update Product Status by its uuid ",
            tags = "Product")
    @PutMapping("update/{id}/status/{status}")
    public ResponseEntity<String> updateProductEnabledStatus(@PathVariable final UUID id,
                                                              @PathVariable final boolean status) {
        productService.updateProductStatusById(id, status);
        String message = "The Product Id " + id + " has been " + status;
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @Operation(
            summary = "Sort & Filter for Products Listing Page",
            description = "Filter Products Listing Page",
            tags = "Product")
    @GetMapping("findByPage/{pageNumber}")
    public List<ProductFindResponseDto> findByPage(@PathVariable("pageNumber") int pageNumber,
                                                   @RequestParam("sortField") String sortField,
                                                   @RequestParam("sortDir") String sortDir,
                                                   @RequestParam("keyword") String keyword) {

        return productService.findByPage(pageNumber, sortField, sortDir, keyword);

    }

    @Operation(
            summary = "Delete existing Product by its uuid",
            description = "Delete Product by uuid",
            tags = "Product")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(
            @PathVariable final UUID id) {
        productService.deleteProductById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(
            summary = "Get Products by Category Id",
            description = "Get Products by Category Id",
            tags = "Product")
    @GetMapping("getByCategoryId/{categoryId}")
    public ResponseEntity<List<ProductFindResponseDto>> getByCategoryId(@PathVariable("categoryId") UUID categoryId) {
        return new ResponseEntity<>(productService.getByCategoryId(categoryId),HttpStatus.OK);
    }

    @Operation(
            summary = "Get Products By Brand Id",
            description = "Get Products By Brand Id",
            tags = "Product")
    @GetMapping("getByBrandId/{brandId}")
    public ResponseEntity<List<ProductFindResponseDto>> getByBrandId(@PathVariable UUID brandId) {
        return new ResponseEntity<>(productService.getByBrandId(brandId), HttpStatus.OK);
    }

    @Operation(
            summary = "Get Products By Category Id and Brand Id",
            description = "Get Products By Category Id and Brand Id",
            tags = "Product")
    @GetMapping("getByCategoryId/{categoryId}/byBrandId/{brandId}")
    public ResponseEntity<List<ProductFindResponseDto>> getByCategoryIdAndBrandId(@PathVariable UUID categoryId,
                                                                                  @PathVariable UUID brandId) {
        return new ResponseEntity<>(productService.getByCategoryIdAndBrandId(categoryId, brandId), HttpStatus.OK);
    }
}