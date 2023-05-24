package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commondto.BrandFineResponesDto;
import com.spring6.ecommerce.dto.BrandCreateRequestDto;
import com.spring6.ecommerce.dto.BrandCreateResponseDto;
import com.spring6.ecommerce.dto.BrandUpdateRequestDto;
import com.spring6.ecommerce.dto.BrandUpdateResponseDto;
import com.spring6.ecommerce.entity.Brand;
import com.spring6.ecommerce.exception.BrandNotFoundException;
import com.spring6.ecommerce.mapper.BrandMapper;
import com.spring6.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public List<BrandFineResponesDto> listAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::brandToBrandFineResponesDto)
                .toList();
    }

    @Override
    public BrandFineResponesDto getById(UUID id) throws BrandNotFoundException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if (optionalBrand.isPresent()) {
            return brandMapper.brandToBrandFineResponesDto(optionalBrand.get());
        }

        throw new BrandNotFoundException("Could not find any brand with ID : " + id);
    }

    @Override
    public BrandCreateResponseDto save(BrandCreateRequestDto brandCreateRequestDto) {
        return brandMapper.brandToBrandCreateResponseDto(brandRepository.save(brandMapper.brandCreateRequestDtoToBrand(brandCreateRequestDto)));

    }

    @Override
    public BrandUpdateResponseDto update(BrandUpdateRequestDto brandCreateRequestDto) {
        return null;
    }

    @Override
    public void deleteById(UUID id) throws BrandNotFoundException {
        Long brandCountById = brandRepository.countById(id);
        if (brandCountById == 0) {
            throw new BrandNotFoundException("Could not find any brand with ID : " + id);
        }
        brandRepository.deleteById(id);
    }

}
