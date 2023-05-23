package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.dto.SubCategoryDto;
import com.spring6.ecommerce.service.SubCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("subCategory")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @GetMapping("list")
    public List<SubCategoryDto> listAll() {
        return subCategoryService.listAll();
    }
}
