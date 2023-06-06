package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
import com.spring6.ecommerce.exception.CategoryNameAlreadyExistException;
import com.spring6.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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

    @PostMapping("addCategory")
    public ResponseEntity<HttpStatus> createCategory(@PathVariable UUID id,@RequestBody @Valid final CategoryCreateRequestDto categoryCreateRequestDto) throws CategoryNameAlreadyExistException {
        if (categoryService.isNameExist(categoryCreateRequestDto.getName())) {
            throw new CategoryNameAlreadyExistException("Category name :" + categoryCreateRequestDto.getName() + " already exist");
        }
        CategoryCreateResponseDto saveCategory = categoryService.createCategories(id,categoryCreateRequestDto);
        return new ResponseEntity( HttpStatus.CREATED);
    }

    @GetMapping("listCategory")
    public List<CategoryFindResponseDto> listAllCategories() {
        return categoryService.listAll();
    }
    @GetMapping("{categoryId}")
    public CategoryFindResponseDto getCategoryById(@PathVariable final UUID categoryId){
        return categoryService.findCategoryById(categoryId);
    }
    @PutMapping("update/{id}")
    public  CategoryUpdateResponseDto updateCategory(@PathVariable UUID id, @RequestBody CategoryUpdateRequestDto categoryDto){
        return categoryService.updateCategory(id, categoryDto);
    }
    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<HttpStatus>  deleteById(@PathVariable final UUID categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
