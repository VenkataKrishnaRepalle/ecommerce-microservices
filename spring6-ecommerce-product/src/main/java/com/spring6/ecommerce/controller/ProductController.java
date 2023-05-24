package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.commondto.ProductFindResponseDto;
import com.spring6.ecommerce.dto.ProductCreateRequestDto;
import com.spring6.ecommerce.dto.ProductCreateResponseDto;
import com.spring6.ecommerce.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("list")
    public List<ProductFindResponseDto> listAllProducts() {
        return productService.listAll();
    }

    @GetMapping("getById/{productId}")
    public ProductFindResponseDto getProductById(@PathVariable UUID productId) {
        return productService.getProductById(productId);
    }

    @PostMapping("addProduct")
    public ResponseEntity<HttpHeaders> createProduct(@RequestBody @Valid final ProductCreateRequestDto productCreateRequestDto) {
        ProductCreateResponseDto savedProduct = productService.addProduct(productCreateRequestDto);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Location","/brand"+savedProduct.getId().toString());
        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
    }
}
