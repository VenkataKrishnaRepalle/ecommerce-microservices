package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commonutil.dto.ParentCategoryFindResponseDto;
import com.spring6.ecommerce.mapper.ParentCategoryMapper;
import com.spring6.ecommerce.repository.ParentCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
