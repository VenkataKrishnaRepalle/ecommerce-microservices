package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.dto.CategoryDto;
import com.spring6.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("category")
public class CategoryController {
    private final CategoryService brandService;

    @GetMapping("list")
    public List<CategoryDto> listAll() {
        return cate.listAll();
    }
}
