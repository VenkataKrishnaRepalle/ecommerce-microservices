package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commondto.ParentCategoryDto;
import com.spring6.ecommerce.mapper.ParentCategoryMapper;
import com.spring6.ecommerce.repository.ParentCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParentCategoryServiceImpl implements ParentCategoryService {
    private final ParentCategoryRepository categoryRepository;
    private final ParentCategoryMapper categoryMapper;


    public List<ParentCategoryDto> listAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryDto)
                .toList();
    }
}
