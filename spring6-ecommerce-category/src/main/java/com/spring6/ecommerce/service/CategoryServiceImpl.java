package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.entity.Category;
import com.spring6.ecommerce.entity.SubCategory;
import com.spring6.ecommerce.exception.SubCategoryNotFoundException;
import com.spring6.ecommerce.exception.CategoryNotFoundException;
import com.spring6.ecommerce.mapper.CategoryMapper;
import com.spring6.ecommerce.repository.SubCategoryRepository;
import com.spring6.ecommerce.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final int CATEGORY_PER_PAGE = 5;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final SubCategoryRepository subCategoryRepository;


    public List<CategoryFindResponseDto> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryFindResponseDto)
                .toList();
    }

    @Override
    public CategoryFindResponseDto findById(UUID id) {
        Optional<Category> parentCategory = categoryRepository.findById(id);
        if (parentCategory.isPresent()) {
            return categoryMapper.categoryToCategoryFindResponseDto(parentCategory.get());
        }

        throw new CategoryNotFoundException("Category does not exist with ID : " + id);
    }

    @Override
    public CategoryCreateResponseDto create(CategoryCreateRequestDto categoryCreateRequestDto) {
        Category category = categoryMapper.categoryCreateRequestDtoToCategory(categoryCreateRequestDto);
        Category savedCategory = categoryRepository.save(category);
        CategoryCreateResponseDto responseDto = categoryMapper.categoryToCategoryCreateResponseDto(savedCategory);
        return responseDto;

//        return parentCategoryMapper.parentCategoryToParentCategoryCreateResponseDto(parentCategoryRepository.save(parentCategoryMapper.parentCategoryCreateRequestDtoToParentCategory(parentCategoryCreateRequestDto)));
    }

    @Override
    public CategoryUpdateResponseDto update(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Optional<Category> optionalParentCategory = categoryRepository.findById(id);
        if (!optionalParentCategory.isPresent()) {
            throw new SubCategoryNotFoundException(" Category does not exist with ID : " + id);
        }


        Category category = categoryMapper.categoryUpdateRequestDtoToCategory(categoryUpdateRequestDto);
        category.setId(optionalParentCategory.get().getId());
        return categoryMapper.categoryToCategoryUpdateResponseDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(UUID id) throws CategoryNotFoundException {

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException(" Category does not exist with ID : " + id);
        }
        Category category = optionalCategory.get();
        for (SubCategory subCategory : subCategoryRepository.findByCategory(category)) {
            subCategoryRepository.delete(subCategory);
        }
        categoryRepository.deleteById(category.getId());

    }

    @Override
    public Boolean isCategoryExistByName(String name) {
        Optional<Category> optionalParentCategory = categoryRepository.findByName(name);
        if (optionalParentCategory.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public boolean isCategoryExistById(UUID id) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public List<CategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, CATEGORY_PER_PAGE, sort);

        if (keyword != null) {
            return categoryRepository.findByPage(keyword, (java.awt.print.Pageable) pageable)
                    .stream()
                    .map(categoryMapper::categoryToCategoryFindResponseDto)
                    .toList();
        }
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryFindResponseDto)
                .toList();

    }

    @Override
    public void updateFileNameById(UUID id, String fileName) {
        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            throw new CategoryNotFoundException("Category could not find with id: " + id);
        }
        Category category = optionalCategory.get();
        category.setImage(fileName);
        categoryRepository.save(category);

    }


}
