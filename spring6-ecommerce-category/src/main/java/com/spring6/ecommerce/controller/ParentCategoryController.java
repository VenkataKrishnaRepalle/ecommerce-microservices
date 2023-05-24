package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.commondto.ParentCategoryFindResponseDto;
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
    private final ParentCategoryService parentCategoryService;

    @GetMapping("list")
    public List<ParentCategoryFindResponseDto> listAll() {
        return parentCategoryService.listAll();
    }
}
