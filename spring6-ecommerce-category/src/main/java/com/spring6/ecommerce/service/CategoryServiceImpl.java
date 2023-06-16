package com.spring6.ecommerce.service;
import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
import com.spring6.ecommerce.entity.Category;
import com.spring6.ecommerce.entity.ParentCategory;
import com.spring6.ecommerce.exception.CategoryNotFoundException;
import com.spring6.ecommerce.exception.parentCategoryNotFoundException;
import com.spring6.ecommerce.mapper.CategoryMapper;
import com.spring6.ecommerce.repository.CategoryRepository;
import com.spring6.ecommerce.repository.ParentCategoryRepository;
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
    private final ParentCategoryRepository parentCategoryRepository;

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
        category.setParentCategory(optionalCategory.get().getParentCategory());
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
    public CategoryCreateResponseDto createCategories(CategoryCreateRequestDto categoryCreateRequestDto)
    {
        Category category = categoryMapper.categoryCreateRequestDtoToCategory(categoryCreateRequestDto);
        UUID parentCategoryId =  categoryCreateRequestDto.getParentCategoryUUID();
        ParentCategory parentCategory = parentCategoryRepository.findById(parentCategoryId).orElseThrow(()-> new parentCategoryNotFoundException("Parent Category does not exist"));
        category.setParentCategory(parentCategory);
        Category savedCategory = categoryRepository.save(category);
        CategoryCreateResponseDto responseDto = categoryMapper.categoryToCategoryCreateResponseDto(savedCategory);
        responseDto.setParentCategoryId(parentCategoryId);
        return responseDto;
    }

    @Override
    public Boolean isNameExist(String name) {
        Optional<Category> optionalCategory = categoryRepository.findByName(name);
        if (optionalCategory.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;    }

    @Override
    public List<Category> findByParentCategory(ParentCategory parentCategory) {
        return categoryRepository.findByParentCategory(parentCategory);
    }

    @Override
    public List<CategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, CATEGORY_PER_PAGE, sort);

        if(keyword != null) {
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
    public boolean isCategoryExist(UUID categoryId) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);

        if (optionalCategory.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public void updateImageFile(UUID categoryId, String fileName) {
        Optional<Category> optionalCategory= categoryRepository.findById(categoryId);

        if (!optionalCategory.isPresent()) {
            throw new CategoryNotFoundException("Could not find any category with ID : " + categoryId);
        }

        Category category = optionalCategory.get();
        category.setImage(fileName);

        categoryRepository.save(category);
    }



}
