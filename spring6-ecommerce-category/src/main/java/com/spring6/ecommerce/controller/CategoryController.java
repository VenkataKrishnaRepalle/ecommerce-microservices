package com.spring6.ecommerce.controller;
import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.exception.CategoryNameAlreadyExistException;
import com.spring6.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("addParentCategory")
    public ResponseEntity<HttpStatus> createParentCategory(@RequestBody @Valid final CategoryCreateRequestDto categoryCreateRequestDto) throws CategoryNameAlreadyExistException {
        if (categoryService.isNameExist(categoryCreateRequestDto.getName())) {
            throw new CategoryNameAlreadyExistException("Parent Category name :" + categoryCreateRequestDto.getName() + " already exist");
        }
        CategoryCreateResponseDto saveParentCategory = categoryService.createParentCategories(categoryCreateRequestDto);
        return new ResponseEntity(saveParentCategory, HttpStatus.CREATED);
    }

    @GetMapping("getList")
    public List<CategoryFindResponseDto> listAll() {
        return categoryService.listAll();
    }

    @GetMapping("{parentCategoryId}")
    public CategoryFindResponseDto getParentCategoryById(@PathVariable final UUID parentCategoryId){
        return categoryService.findCategoryById(parentCategoryId);
    }
    @PutMapping("update/{id}")
    public CategoryUpdateResponseDto updateParentCategory(@PathVariable UUID id, @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto){
        return categoryService.updateParentCategory(id, categoryUpdateRequestDto);

    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable final UUID id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
