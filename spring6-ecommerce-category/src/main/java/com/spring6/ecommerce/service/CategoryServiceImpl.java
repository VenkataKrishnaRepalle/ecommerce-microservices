package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commondto.CategoryFindResponseDto;
import com.spring6.ecommerce.mapper.CategoryMapper;
import com.spring6.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public List<CategoryFindResponseDto> listAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryFindResponseDto)
                .toList();
    }
}
