package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commonutil.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
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

    @Override
    public CategoryUpdateResponseDto updateCategory(final UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto) throws CategoryNotFoundException {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new CategoryNotFoundException("Category does not exist with ID : " + id);
        }
        Category category = categoryMapper.categoryUpdateRequestDtoToCategory(categoryUpdateRequestDto);
        category.setId(id);
        return categoryMapper.categoryToCategoryUpdateResponseDto(categoryRepository.save(category));
    }

    @Override
    public void deleteCategoryById(UUID categoryId) throws CategoryNotFoundException {
        Long categoryCountById = categoryRepository.countById(categoryId);
        if (categoryCountById == 0) {
            throw new CategoryNotFoundException("Category does not exist with ID : " + categoryId);
        }
        categoryRepository.deleteById(categoryId);

    }

    @Override
    public CategoryCreateResponseDto createCategories(CategoryCreateRequestDto categoryCreateRequestDto) {

        return categoryMapper.categoryToCategoryCreateResponseDto(categoryRepository.save(categoryMapper.categoryCreateRequestDtoToCategory(categoryCreateRequestDto)));
    }

}
