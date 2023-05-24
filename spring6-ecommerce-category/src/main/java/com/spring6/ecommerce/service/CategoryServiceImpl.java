package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commondto.CategoryDto;
import com.spring6.ecommerce.mapper.CategoryMapper;
import com.spring6.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository subCategoryRepository;
    private final CategoryMapper subCategoryMapper;

    public List<CategoryDto> listAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::subCategoryToSubCategoryDto)
                .toList();
    }
}
