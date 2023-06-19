package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.common.dto.ProductDetailsFindResponseDto;
import com.spring6.ecommerce.service.ProductDetailsService;
import com.spring6.ecommerce.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequestMapping("productDetails")
public class ProductDetailsController {

    @Autowired
    private ProductDetailsService productDetailsService;
    @Operation(summary = "Add Product Details", description = "Add Product Details", tags = "Product Details")
    @PostMapping("/addProductDetails/{id}")
    public ResponseEntity<List<ProductDetailsFindResponseDto>> addProductDetails(@PathVariable("id") UUID id,
                                                                                 @RequestParam("detailNames") String[] detailNames,
                                                                                 @RequestParam("detailValue") String[] detailValues) {
        List<ProductDetailsFindResponseDto> productDetailsCreateResponseDtoList = productDetailsService.addProductDetails(id, detailNames, detailValues);
        return new ResponseEntity<>(productDetailsCreateResponseDtoList, HttpStatus.CREATED);

    }

    @Operation(summary = "Update Product Details", description = "Update Product Details", tags = "Product Details")
    @PutMapping("/updateProductDetails/{id}")
    public ResponseEntity<List<ProductDetailsFindResponseDto>> updateProductDetails(@PathVariable("id") UUID id,
                                                                                    @RequestParam("detailName") String[] detailName,
                                                                                    @RequestParam("detailValue") String[] detailValue) {
        return new ResponseEntity<>(productDetailsService.updateProductDetails(id, detailName,detailValue), HttpStatus.ACCEPTED);
    };

}
