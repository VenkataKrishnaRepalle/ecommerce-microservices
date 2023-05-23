package com.spring6.ecommerce.service;
import com.spring6.ecommerce.dto.SubCategoryDto;
import com.spring6.ecommerce.mapper.SubCategoryMapper;
import com.spring6.ecommerce.repository.SubCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;

    public List<SubCategoryDto> listAll() {
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::
                        subCategoryToSubCategoryDto)
                .toList();
    }
}
