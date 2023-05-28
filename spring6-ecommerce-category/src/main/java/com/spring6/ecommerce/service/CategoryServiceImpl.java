package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commonutil.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.entity.Category;
import com.spring6.ecommerce.exception.CategoryNotFoundException;
import com.spring6.ecommerce.mapper.CategoryMapper;
import com.spring6.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

    @Override
    public CategoryFindResponseDto findCategoryById(UUID id) throws CategoryNotFoundException {
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            return categoryMapper.categoryToCategoryFindResponseDto(category.get());
        }

        throw new CategoryNotFoundException("Category does not exist with ID : " + id);
    }

}
