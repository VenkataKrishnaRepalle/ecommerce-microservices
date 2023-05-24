package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commondto.BrandDto;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    List<BrandDto> listAll();

    BrandDto save(BrandDto brandDto);

    BrandDto getById(UUID id);

    void deleteById(UUID id);
}
