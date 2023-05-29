package com.spring6.ecommerce.controller;
import com.spring6.ecommerce.commonutil.dto.ParentCategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.service.ParentCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("parentCategory")
public class ParentCategoryController {
    private final ParentCategoryService parentCategoryService;
    @GetMapping("listParentCategory")
    public List<ParentCategoryFindResponseDto> listAll() {
        return parentCategoryService.listAll();
    }
//    @PostMapping("addCategory")
//    public ResponseEntity<HttpHeaders> createCategory(@RequestBody @Valid final CategoryCreateRequestDto categoryCreateRequestDto) {
//        CategoryCreateResponseDto saveCategory = categoryService.createCategories(categoryCreateRequestDto);
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.add("Location","/brand"+saveCategory.getId().toString());
//        return new ResponseEntity(httpHeaders, HttpStatus.CREATED);
//    }
}
