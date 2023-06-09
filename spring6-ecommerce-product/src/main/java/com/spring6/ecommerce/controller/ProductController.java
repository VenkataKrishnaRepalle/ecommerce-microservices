package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.common.dto.ProductDetailsFindResponseDto;
import com.spring6.ecommerce.common.dto.ProductFindResponseDto;
import com.spring6.ecommerce.common.utils.FileUploadUtils;
import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.entity.ProductDetails;
import com.spring6.ecommerce.entity.ProductImage;
import com.spring6.ecommerce.exception.ProductAlreadyPresentException;
import com.spring6.ecommerce.exception.ProductNotFoundException;
import com.spring6.ecommerce.service.ProductService;
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
    @PostMapping(value = "addProduct")
    public ResponseEntity<HttpHeaders> createProduct(@RequestBody @Valid
            final ProductCreateRequestDto productCreateRequestDto) {
        if(productService.isProductNameExists(productCreateRequestDto.getName())) {
                throw new ProductAlreadyPresentException("Product Already present with name "+productCreateRequestDto.getName());
        }
        ProductCreateResponseDto savedProduct = productService.addProduct(productCreateRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location", "/brand" + savedProduct.getId().toString());
        return new ResponseEntity(savedProduct, httpHeaders, HttpStatus.CREATED);
    }

    @Operation(summary = "Insert Product Images", description = "Add Product Image", tags = "Product Image")
    @PostMapping(value = "addProductImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> addProductImage (@RequestParam @NotNull final UUID productId,
                                              @NotNull @RequestParam(name = "fileImage", value = "fileImage") final MultipartFile multipartFile)
                                                throws IOException , ProductNotFoundException {
        if(!productService.isProductExists(productId))
        {
            throw new ProductNotFoundException("Couldn't found product with id "+ productId);
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uploadDir = "spring6-ecommerce-product/product-images";
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());

            productService.updateImageName(productId, fileName);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @Operation(summary = "Add Product Details", description = "Add Product Details", tags = "Product Details")
    @PostMapping("/addProductDetails")
    public ResponseEntity<List<ProductDetailsFindResponseDto>> addProductDetails(@RequestParam("productId") UUID productId,
                                                                                 @RequestParam("detailNames") String[] detailNames,
                                                                                 @RequestParam("detailValue") String[] detailValues) {
        List<ProductDetailsFindResponseDto> productDetailsCreateResponseDtoList= productService.addProductDetails(productId, detailNames, detailValues);
        return new ResponseEntity<>(productDetailsCreateResponseDtoList, HttpStatus.CREATED);

    }

    @Operation(summary = "Update Product", description = "Update Product", tags = "Product")
    @PutMapping("/updateProduct/{productId}")
    public ResponseEntity<ProductUpdateResponseDto> updateProduct(@PathVariable("productId") UUID productId,
                                                                  @RequestBody ProductUpdateRequestDto productUpdateRequestDto) {
        ProductUpdateResponseDto product = productService.updateProduct(productId, productUpdateRequestDto);
        return new ResponseEntity<>(product, HttpStatus.ACCEPTED);
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
