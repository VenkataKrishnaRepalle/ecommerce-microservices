package com.spring6.ecommerce.service;


import com.spring6.ecommerce.commondto.CategoryFindResponseDto;

import java.util.List;

public interface CategoryService {
    List<CategoryFindResponseDto> listAll();
}
