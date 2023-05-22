package com.spring6.ecommerce.service;

import com.spring6.ecommerce.dto.CategoryDto;
import com.spring6.ecommerce.repository.CategoryRepository;

import java.util.List;
import java.util.stream.Collectors;

public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<CategoryDto> listAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .collect(Collectors.toList());
    }
}
