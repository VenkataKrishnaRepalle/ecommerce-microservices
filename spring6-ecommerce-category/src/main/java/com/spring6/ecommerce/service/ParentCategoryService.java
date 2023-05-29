package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commonutil.dto.ParentCategoryFindResponseDto;

import java.util.List;

public interface ParentCategoryService {
    List<ParentCategoryFindResponseDto> listAll();


}
