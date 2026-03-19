package com.pm.spring.ema.controller;

import com.pm.spring.ema.common.dto.ProductDetailsFindResponseDto;
import com.pm.spring.ema.permission.ProductDetailsService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@Transactional(propagation = Propagation.REQUIRED)
@RequestMapping("api/productDetails")
public class ProductDetailsController {

  @Autowired private ProductDetailsService productDetailsService;

  @Operation(
      summary = "Get Product Details by Product Id",
      description = "View Product Details by Product Id",
      tags = "Product Details")
  @GetMapping("{id}")
  public ResponseEntity<List<ProductDetailsFindResponseDto>> getByProductId(
      @PathVariable("id") UUID id) {
    return new ResponseEntity<>(productDetailsService.getByProductId(id), HttpStatus.OK);
  }

  @Operation(
      summary = "Add Product Details",
      description = "Add Product Details",
      tags = "Product Details")
  @PostMapping("/create/{id}")
  public ResponseEntity<List<ProductDetailsFindResponseDto>> create(
      @PathVariable("id") UUID id,
      @RequestParam("detailNames") String[] detailNames,
      @RequestParam("detailValue") String[] detailValues) {
    List<ProductDetailsFindResponseDto> productDetailsCreateResponseDtoList =
        productDetailsService.create(id, detailNames, detailValues);
    return new ResponseEntity<>(productDetailsCreateResponseDtoList, HttpStatus.CREATED);
  }

  @Operation(
      summary = "Update Product Details",
      description = "Update Product Details",
      tags = "Product Details")
  @PutMapping("/update/{id}")
  public ResponseEntity<List<ProductDetailsFindResponseDto>> update(
      @PathVariable("id") UUID id,
      @RequestParam("detailName") String[] detailName,
      @RequestParam("detailValue") String[] detailValue) {
    return new ResponseEntity<>(
        productDetailsService.update(id, detailName, detailValue), HttpStatus.ACCEPTED);
  }
  ;
}
