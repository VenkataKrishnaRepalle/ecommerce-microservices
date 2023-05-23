package com.spring6.ecommerce.service;

import com.spring6.ecommerce.dto.BrandDto;
import com.spring6.ecommerce.entity.Brand;
import com.spring6.ecommerce.exception.BrandNotFoundException;
import com.spring6.ecommerce.mapper.BrandMapper;
import com.spring6.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
                .toList();
    }

    @Override
    public BrandDto save(BrandDto brandDto) {
        return brandMapper.brandToBrandDto(brandRepository.save(brandMapper.brandDtoToBrand(brandDto)));

    }

    @Override
    public BrandDto getById(UUID id) throws BrandNotFoundException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if (optionalBrand.isPresent()) {
            return brandMapper.brandToBrandDto(optionalBrand.get());
        }

        throw new BrandNotFoundException("Could not find any brand with ID : " + id);
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
