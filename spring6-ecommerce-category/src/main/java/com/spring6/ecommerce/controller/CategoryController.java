package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.commonutil.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("listCategory")
    public List<CategoryFindResponseDto> listAllCategories() {
        return categoryService.listAll();
    }
    @GetMapping("{categoryId}")
    public CategoryFindResponseDto getCategoryById(@PathVariable final UUID categoryId){
        return categoryService.findCategoryById(categoryId);
    }

}
