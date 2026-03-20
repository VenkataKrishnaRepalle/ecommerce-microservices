package com.pm.spring.ema.product.controller;

import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.common.util.dto.ProductDto;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequiredArgsConstructor
@RequestMapping("api/product")
public class ProductController {

  private final ProductService productService;

  @Operation(summary = "Get List of Products", description = "List of Products", tags = "Product")
  @GetMapping("list")
  public ResponseEntity<List<ProductDto>> findAll() {
    return new ResponseEntity<>(productService.listAll(), HttpStatus.OK);
  }

  @Operation(summary = "Get Product by uuid", description = "Get Product by uuid", tags = "Product")
  @GetMapping("{id}")
  public ResponseEntity<ProductDto> findById(@PathVariable final UUID id) {
    return new ResponseEntity<>(productService.getProductById(id), HttpStatus.OK);
  }

  @Operation(summary = "Insert Product", description = "Add Product", tags = "Product")
  @PostMapping(value = "create")
  public ResponseEntity<ProductDto> create(@RequestBody @Valid final ProductDto productDto) {
    var savedProduct = productService.create(productDto);
    HttpHeaders httpHeaders = new HttpHeaders();
    httpHeaders.add("Location", "/brand" + savedProduct.getId());
    return new ResponseEntity<>(savedProduct, httpHeaders, HttpStatus.CREATED);
  }

  @Operation(
      summary = "Insert Product Images",
      description = "Add Product Image",
      tags = "Product Image")
  @PostMapping(value = "upload-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<HttpStatus> uploadImage(
      @PathVariable @NotNull final UUID id,
      @NotNull @RequestParam(name = "fileImage", value = "fileImage")
          final MultipartFile multipartFile)
      throws IOException {
    if (!productService.isProductExists(id)) {
      throw new NotFoundException(ErrorCodes.E2001, String.valueOf(id));
    }

    if (!multipartFile.isEmpty()) {
      var fileName =
          StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

      var uploadDir = "spring6-ecommerce-product/product-images";
      FileUploadUtils.saveFile(uploadDir, fileName + "_" + id, multipartFile.getInputStream());

      productService.uploadImage(id, fileName);
    }
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @Operation(summary = "Update Product", description = "Update Product", tags = "Product")
  @PutMapping("update/{id}")
  public ResponseEntity<ProductDto> update(
      @PathVariable UUID id, @RequestBody ProductDto productDto) {
    return new ResponseEntity<>(productService.update(id, productDto), HttpStatus.ACCEPTED);
  }

  @Operation(
      summary = "Update existing Product Status by uuid",
      description = "Update Product Status by its uuid ",
      tags = "Product")
  @PutMapping("update/{id}/status/{status}")
  public ResponseEntity<String> updateProductEnabledStatus(
      @PathVariable final UUID id, @PathVariable final boolean status) {
    productService.updateProductStatusById(id, status);
    var message = "The Product Id " + id + " has been " + status;
    return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
  }

  @Operation(
      summary = "Sort & Filter for Products Listing Page",
      description = "Filter Products Listing Page",
      tags = "Product")
  @GetMapping("find-by-page/{pageNumber}")
  public List<ProductDto> findByPage(
      @PathVariable int pageNumber,
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
  public ResponseEntity<HttpStatus> deleteById(@PathVariable final UUID id) {
    productService.deleteProductById(id);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Operation(
      summary = "Get Products By Brand Id",
      description = "Get Products By Brand Id",
      tags = "Product")
  @GetMapping("get-by-brandId/{brandId}")
  public ResponseEntity<List<ProductDto>> getByBrandId(@PathVariable UUID brandId) {
    return new ResponseEntity<>(productService.getByBrandId(brandId), HttpStatus.OK);
  }

  @Operation(
      summary = "Get is Product Exists By Product Id",
      description = "Get is Product Exists By Product Id",
      tags = "Product")
  @GetMapping("isProductExistsById/{id}")
  public ResponseEntity<Boolean> isProductExistsById(@PathVariable UUID id) {
    return new ResponseEntity<>(productService.isProductExistsById(id), HttpStatus.OK);
  }
}
