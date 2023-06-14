package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.ParentCategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
import com.spring6.ecommerce.dto.ParentCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.ParentCategoryUpdateResponseDto;
import com.spring6.ecommerce.entity.Category;
import com.spring6.ecommerce.entity.ParentCategory;
import com.spring6.ecommerce.exception.CategoryNotFoundException;
import com.spring6.ecommerce.exception.parentCategoryNotFoundException;
import com.spring6.ecommerce.mapper.ParentCategoryMapper;
import com.spring6.ecommerce.repository.ParentCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ParentCategoryServiceImpl implements ParentCategoryService {
    private final ParentCategoryRepository parentCategoryRepository;
    private final ParentCategoryMapper parentCategoryMapper;


    public List<ParentCategoryFindResponseDto> listAll() {
        return parentCategoryRepository.findAll()
                .stream()
                .map(parentCategoryMapper::parentCategoryToParentCategoryFindResponseDto)
                .toList();
    }

    @Override
    public ParentCategoryFindResponseDto findCategoryById(UUID parentCategoryId) {
        Optional<ParentCategory> parentCategory = parentCategoryRepository.findById(parentCategoryId);
        if (parentCategory.isPresent()) {
            return parentCategoryMapper.parentCategoryToParentCategoryFindResponseDto(parentCategory.get());
        }

        throw new parentCategoryNotFoundException("Parent Category does not exist with ID : " + parentCategoryId);
    }

    @Override
    public ParentCategoryUpdateResponseDto updateParentCategory(UUID id, ParentCategoryUpdateRequestDto parentCategoryUpdateRequestDto) {
        Optional<ParentCategory> optionalParentCategory = parentCategoryRepository.findById(id);
        if (!optionalParentCategory.isPresent()) {
            throw new CategoryNotFoundException("Parent Category does not exist with ID : " + id);
        }


        ParentCategory parentCategory = parentCategoryMapper.parentCategoryUpdateRequestDtoToParentCategory(parentCategoryUpdateRequestDto);
        parentCategory.setId(optionalParentCategory.get().getId());
        return parentCategoryMapper.parentCategoryToParentCategoryUpdateResponseDto(parentCategoryRepository.save(parentCategory));
    }

    @Override
    public void deleteById(UUID id) throws parentCategoryNotFoundException {
        Long parentCategoryCountById = parentCategoryRepository.countById(id);
        if (parentCategoryCountById == 0) {
            throw new CategoryNotFoundException("Category does not exist with ID : " + id);
        }
        parentCategoryRepository.deleteById(id);

    }


}
