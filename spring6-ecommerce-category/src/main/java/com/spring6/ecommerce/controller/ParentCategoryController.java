package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.commondto.ParentCategoryDto;
import com.spring6.ecommerce.service.ParentCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("parent-category")
public class ParentCategoryController {
    private final ParentCategoryService categoryService;

    @GetMapping("list")
    public List<ParentCategoryDto> listAll() {
        return categoryService.listAll();
    }
}
