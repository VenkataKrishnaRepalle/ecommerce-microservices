package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService productService;

//    @GetMapping("list")
//    public List<ProductFindResponseDto> listAllProducts() {
//        return productService.listAll();
//    }
//
//    @GetMapping("getById/{productId}")
//    public ProductFindResponseDto getProductById(@PathVariable UUID productId) {
//        return productService.getProductById(productId);
//    }
}
