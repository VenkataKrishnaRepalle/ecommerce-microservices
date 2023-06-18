package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.SubCategoryFindResponseDto;
import com.spring6.ecommerce.dto.SubCategoryCreateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryCreateResponseDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateResponseDto;
import com.spring6.ecommerce.entity.Category;
import com.spring6.ecommerce.entity.SubCategory;
import com.spring6.ecommerce.exception.SubCategoryNotFoundException;
import com.spring6.ecommerce.exception.CategoryNotFoundException;
import com.spring6.ecommerce.mapper.SubCategoryMapper;
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
public class SubCategoryServiceImpl implements SubCategoryService {
    private static final int SUB_CATEGORY_PER_PAGE = 5;
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;
    private final CategoryRepository categoryRepository;

    public List<SubCategoryFindResponseDto> findAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::subCategoryToSubCategoryFindResponseDto)
                .toList();
    }

    @Override
    public SubCategoryFindResponseDto findById(UUID id) throws SubCategoryNotFoundException {
        Optional<SubCategory> category = subCategoryRepository.findById(id);
        if (category.isPresent()) {
            return subCategoryMapper.subCategoryToSubCategoryFindResponseDto(category.get());
        }

        throw new SubCategoryNotFoundException("Category does not exist with ID : " + id);
    }

    @Override
    public SubCategoryUpdateResponseDto update(final UUID id, SubCategoryUpdateRequestDto subCategoryUpdateRequestDto) throws SubCategoryNotFoundException {
        Optional<SubCategory> optionalCategory = subCategoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new SubCategoryNotFoundException("Category does not exist with ID : " + id);
        }


        SubCategory subCategory = subCategoryMapper.subCategoryUpdateRequestDtoToSubCategory(subCategoryUpdateRequestDto);
        subCategory.setId(id);
        subCategory.setCategory(optionalCategory.get().getCategory());
        return subCategoryMapper.subCategoryToSubCategoryUpdateResponseDto(subCategoryRepository.save(subCategory));
    }

    @Override
    public void deleteById(UUID categoryId) throws SubCategoryNotFoundException {
        Long categoryCountById = subCategoryRepository.countById(categoryId);
        if (categoryCountById == 0) {
            throw new SubCategoryNotFoundException("Category does not exist with ID : " + categoryId);
        }
        subCategoryRepository.deleteById(categoryId);

    }

    @Override
    public SubCategoryCreateResponseDto create(SubCategoryCreateRequestDto subCategoryCreateRequestDto) {
        SubCategory subCategory = subCategoryMapper.subCategoryCreateRequestDtoToSubCategory(subCategoryCreateRequestDto);
        UUID categoryId = subCategoryCreateRequestDto.getCategoryUUID();
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException("Category does not exist"));
        subCategory.setCategory(category);
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        SubCategoryCreateResponseDto responseDto = subCategoryMapper.subCategoryToSubCategoryCreateResponseDto(savedSubCategory);
        responseDto.setCategoryUUID(categoryId);
        return responseDto;
    }

    @Override
    public Boolean isSubCategoryExistByName(String name) {
        Optional<SubCategory> optionalCategory = subCategoryRepository.findByName(name);
        if (optionalCategory.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public List<SubCategoryFindResponseDto> findByCategoryId(UUID categoryId) {

        return subCategoryRepository.findByCategory(Category.builder().id(categoryId).build())
                .stream()
                .map(subCategoryMapper::subCategoryToSubCategoryFindResponseDto)
                .toList();
    }


    @Override
    public List<SubCategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, SUB_CATEGORY_PER_PAGE, sort);

        if (keyword != null) {
            return subCategoryRepository.findByPage(keyword, (java.awt.print.Pageable) pageable)
                    .stream()
                    .map(subCategoryMapper::subCategoryToSubCategoryFindResponseDto)
                    .toList();
        }
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::subCategoryToSubCategoryFindResponseDto)
                .toList();

    }

    @Override
    public boolean isSubCategoryExistById(UUID id) {
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(id);

        if (optionalSubCategory.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public void updateFileNameById(UUID id, String fileName) {
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(id);

        if (optionalSubCategory.isEmpty()) {
            throw new SubCategoryNotFoundException("Could not find any sub-category with ID : " + id);
        }

        SubCategory subCategory = optionalSubCategory.get();
        subCategory.setImage(fileName);

        subCategoryRepository.save(subCategory);
    }


}
