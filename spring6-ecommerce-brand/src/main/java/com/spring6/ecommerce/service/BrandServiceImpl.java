package com.spring6.ecommerce.service;

import com.spring6.ecommerce.dto.BrandDto;
import com.spring6.ecommerce.mapper.BrandMapper;
import com.spring6.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public List<BrandDto> listAll() {
       return brandRepository.findAll()
               .stream()
               .map(brandMapper::brandToBrandDto)
               .collect(Collectors.toList());
    }
}
