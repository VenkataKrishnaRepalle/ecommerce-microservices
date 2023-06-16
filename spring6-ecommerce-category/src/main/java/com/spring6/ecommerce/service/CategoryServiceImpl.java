package com.spring6.ecommerce.service;
import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.entity.Category;
import com.spring6.ecommerce.exception.SubCategoryNotFoundException;
import com.spring6.ecommerce.exception.CategoryNotFoundException;
import com.spring6.ecommerce.mapper.CategoryMapper;
import com.spring6.ecommerce.repository.SubCategoryRepository;
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
    private final SubCategoryService subCategoryService;
    private final SubCategoryRepository subCategoryRepository;




    public List<CategoryFindResponseDto> listAll() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::parentCategoryToParentCategoryFindResponseDto)
                .toList();
    }

    @Override
    public CategoryFindResponseDto findCategoryById(UUID parentCategoryId) {
        Optional<Category> parentCategory = categoryRepository.findById(parentCategoryId);
        if (parentCategory.isPresent()) {
            return categoryMapper.parentCategoryToParentCategoryFindResponseDto(parentCategory.get());
        }

        throw new CategoryNotFoundException("Parent Category does not exist with ID : " + parentCategoryId);
    }

    @Override
    public CategoryUpdateResponseDto updateParentCategory(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto) {
        Optional<Category> optionalParentCategory = categoryRepository.findById(id);
        if (!optionalParentCategory.isPresent()) {
            throw new SubCategoryNotFoundException("Parent Category does not exist with ID : " + id);
        }


        Category category = categoryMapper.parentCategoryUpdateRequestDtoToParentCategory(categoryUpdateRequestDto);
        category.setId(optionalParentCategory.get().getId());
        return categoryMapper.parentCategoryToParentCategoryUpdateResponseDto(categoryRepository.save(category));
    }

    @Override
    public void deleteById(UUID id) throws CategoryNotFoundException {

        Optional<Category> optionalParentCategory = categoryRepository.findById(id);
        if (optionalParentCategory.isEmpty()) {
            throw new CategoryNotFoundException("Parent Category does not exist with ID : " + id);
        }

        Category category = optionalParentCategory.get();
        categoryRepository.deleteById(category.getId());

    }

    @Override
    public Boolean isNameExist(String name) {
        Optional<Category> optionalParentCategory = categoryRepository.findByName(name);
        if (optionalParentCategory.isPresent()){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public CategoryCreateResponseDto createParentCategories(CategoryCreateRequestDto categoryCreateRequestDto) {
       Category category = categoryMapper.parentCategoryCreateRequestDtoToParentCategory(categoryCreateRequestDto);
       Category savedCategory = categoryRepository.save(category);
       CategoryCreateResponseDto responseDto = categoryMapper.parentCategoryToParentCategoryCreateResponseDto(savedCategory);
       return responseDto;

//        return parentCategoryMapper.parentCategoryToParentCategoryCreateResponseDto(parentCategoryRepository.save(parentCategoryMapper.parentCategoryCreateRequestDtoToParentCategory(parentCategoryCreateRequestDto)));
   }


}
