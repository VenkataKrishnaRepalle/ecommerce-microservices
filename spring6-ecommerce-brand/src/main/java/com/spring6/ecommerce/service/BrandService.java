package com.spring6.ecommerce.service;

import com.spring6.ecommerce.dto.BrandDto;
import com.spring6.ecommerce.entity.Brand;

import java.util.List;

public interface BrandService {
    List<BrandDto> listAll();
}
