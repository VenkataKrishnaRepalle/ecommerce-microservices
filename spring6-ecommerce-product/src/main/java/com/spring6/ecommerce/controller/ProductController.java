package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.common.dto.ProductFindResponseDto;
import com.spring6.ecommerce.common.utils.FileUploadUtils;
import com.spring6.ecommerce.dto.ProductCreateRequestDto;
import com.spring6.ecommerce.dto.ProductCreateResponseDto;
import com.spring6.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Operation(summary = "Get List of Products", description = "List of Products", tags = "Product")
    @GetMapping("list")
    public List<ProductFindResponseDto> listAllProducts() {
        return productService.listAll();
    }

    @Operation(summary = "Get Product by uuid", description = "Product by uuid", tags = "Product")
    @GetMapping("getById/{productId}")
    public ProductFindResponseDto getProductById(@PathVariable final
                                                     UUID productId) {
        return productService.getProductById(productId);
    }

    @Operation(summary = "Insert Product", description = "Add Product", tags = "Product")
    @PostMapping(value = "addProduct", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.MULTIPART_FORM_DATA_VALUE )
    public ResponseEntity<HttpHeaders> createProduct(@RequestBody @Valid
            final ProductCreateRequestDto productCreateRequestDto,
            @RequestPart("FileImage") final MultipartFile multipartFile)
            throws IOException {
        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(
                    multipartFile.getOriginalFilename());
            productCreateRequestDto.setMainImage(fileName);

            ProductCreateResponseDto savedProduct =
                    productService.addProduct(productCreateRequestDto);

            String uploadDir = "../brand-logos";

            FileUploadUtils.cleanDir(uploadDir);

            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());
        }
        ProductCreateResponseDto savedProduct =
                productService.addProduct(productCreateRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type","multipart/form-data; charset=utf-8");
        httpHeaders.add("Location", "/brand" + savedProduct.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Update existing Product Status by uuid", description = "Update Product Status by its uuid ", tags = "Product")
    @PutMapping("update/{productId}/enabled/{status}")
    public ResponseEntity<String> updateCategoryEnabledStatus(
            @PathVariable final UUID productId,
            @PathVariable final boolean status) {
        productService.updateProductEnabledStatus(productId, status);
        String message = "The Product Id " + productId + " has been " + status;
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @Operation(summary = "Delete existing Product by its uuid", description = "Delete Product by uuid", tags = "Product")
    @DeleteMapping("delete/{productId}")
    public ResponseEntity<HttpStatus> deleteProductById(
            @PathVariable final UUID productId) {
        productService.deleteProductById(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
