package com.spring6.ecommerce.service;


import com.spring6.ecommerce.commondto.CategoryDto;

import java.util.List;

public interface CategoryService {
    List<CategoryDto> listAll();
}
