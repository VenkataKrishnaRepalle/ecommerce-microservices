package com.spring6.ecommerce.service;

import com.spring6.ecommerce.common.dto.BrandFindResponesDto;
import com.spring6.ecommerce.dto.BrandCreateRequestDto;
import com.spring6.ecommerce.dto.BrandCreateResponseDto;
import com.spring6.ecommerce.dto.BrandUpdateRequestDto;
import com.spring6.ecommerce.dto.BrandUpdateResponseDto;
import com.spring6.ecommerce.entity.Brand;
import com.spring6.ecommerce.exception.BrandNotFoundException;
import com.spring6.ecommerce.mapper.BrandMapper;
import com.spring6.ecommerce.repository.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {
    public static final int BRANDS_PER_PAGE = 2;
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public List<BrandFindResponesDto> findAll() {
        return brandRepository.findAll()
                .stream()
                .map(brandMapper::brandToBrandFineResponesDto)
                .toList();
    }

    public List<BrandFindResponesDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, BRANDS_PER_PAGE, sort);

        if (keyword != null) {
            return brandRepository.findAll(keyword, pageable)
                    .stream()
                    .map(brandMapper::brandToBrandFineResponesDto)
                    .toList();
        }
        return brandRepository.findAll(pageable)
                .stream()
                .map(brandMapper::brandToBrandFineResponesDto)
                .toList();

    }

    @Override
    public BrandFindResponesDto findById(UUID id) throws BrandNotFoundException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if (optionalBrand.isPresent()) {
            return brandMapper.brandToBrandFineResponesDto(optionalBrand.get());
        }

        throw new BrandNotFoundException("Could not find any brand with ID : " + id);
    }

    @Override
    public BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto) {
        return brandMapper.brandToBrandCreateResponseDto(
                brandRepository.save(brandMapper.brandCreateRequestDtoToBrand(brandCreateRequestDto))
        );

    }

    @Override
    public BrandUpdateResponseDto update(final UUID id, BrandUpdateRequestDto brandUpdateRequestDto)
            throws BrandNotFoundException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if (!optionalBrand.isPresent()) {
            throw new BrandNotFoundException("Could not find any brand with ID : " + id);
        }

        Brand brand = brandMapper.brandUpdateRequestDtoToBrand(brandUpdateRequestDto);
        brand.setId(optionalBrand.get().getId());

        return brandMapper.brandToBrandUpdateResponseDto(brandRepository.save(brand));
    }

    @Override
    public void deleteById(UUID id) throws BrandNotFoundException {
        Long brandCountById = brandRepository.countById(id);
        if (brandCountById == 0) {
            throw new BrandNotFoundException("Could not find any brand with ID : " + id);
        }
        brandRepository.deleteById(id);
    }

    @Override
    public Boolean isNameExist(String name) {
        Optional<Brand> optionalBrand = brandRepository.findByName(name);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isIdExist(UUID uuid) {
        Optional<Brand> optionalBrand = brandRepository.findById(uuid);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public void updateImageName(UUID brandId, String fileName) {
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);

        if (!optionalBrand.isPresent()) {
            throw new BrandNotFoundException("Could not find any brand with ID : " + brandId);
        }

        Brand brand = optionalBrand.get();
        brand.setLogo(fileName);

        brandRepository.save(brand);
    }

}
