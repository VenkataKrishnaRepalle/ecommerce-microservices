package com.spring6.brand.service;

import com.spring6.brand.dto.BrandCreateRequestDto;
import com.spring6.brand.dto.BrandCreateResponseDto;
import com.spring6.brand.dto.BrandUpdateRequestDto;
import com.spring6.brand.dto.BrandUpdateResponseDto;
import com.spring6.brand.entity.Brand;
import com.spring6.brand.exception.BrandNameAlreadyExistException;
import com.spring6.brand.mapper.BrandMapper;
import com.spring6.common.dto.BrandFindResponseDto;
import com.spring6.common.exeption.ErrorCode;
import com.spring6.brand.enums.BrandSearchKeywordEnum;
import com.spring6.brand.exception.BrandNotFoundException;
import com.spring6.brand.repository.BrandRepository;
import com.spring6.common.utils.GlobalConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;
    private String traceId = MDC.get(GlobalConstants.TRACE_ID);


    public List<BrandFindResponseDto> findAll() {
        log.info("BrandService:findAll execution started.");
        log.info("BrandService:findAll traceId: {}", traceId);

        List<BrandFindResponseDto> brandFindResponseDtoList = brandRepository.findAll()
                .stream()
                .map(brandMapper::brandToBrandFindResponesDto)
                .toList();

        log.info("BrandService:findAll traceId: {}, response {} ", traceId, brandFindResponseDtoList);
        log.info("BrandService:findAll execution ended.");

        return brandFindResponseDtoList;
    }

    public List<BrandFindResponseDto> findByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDirectory, BrandSearchKeywordEnum searchField, String searchKeyword) {
        log.info("BrandService:findByPage execution started.");
        log.info("BrandService:findByPage traceId: {},  pageNumber: {}, perPageCount: {}, sortField: {}, sortDirectory: {}, searchField: {}, searchKeyword: {}", traceId, pageNumber, perPageCount, sortField, sortDirectory, searchField, searchKeyword);

        if (sortField.isBlank()) {
            sortField = "name";

        }
        Sort sort = Sort.by(sortField);
        sort = sortDirectory.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);

        if (searchKeyword != null && searchField.equals(BrandSearchKeywordEnum.BRAND_NAME)) {
            log.info("BrandService:findByPage traceId: {}", traceId);
            log.info("BrandService:findByPage execution ended.");
            return brandRepository.findAllByName(searchKeyword, pageable)
                    .stream()
                    .map(brandMapper::brandToBrandFindResponesDto)
                    .toList();
        }

        log.info("BrandService:findByPage traceId: {}", traceId);
        log.info("BrandService:findByPage execution ended.");

        return brandRepository.findAll(pageable)
                .stream()
                .map(brandMapper::brandToBrandFindResponesDto)
                .toList();

    }

    @Override
    public BrandFindResponseDto findById(UUID id) throws BrandNotFoundException {
        log.info("BrandService:findById execution started.");
        log.info("BrandService:findById traceId: {} , id: {}", traceId, id);

        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if (!optionalBrand.isPresent()) {
            log.error("BrandService:findByPage traceId: {} Brand Not found", traceId);
            log.info("BrandService:findByPage execution ended.");
            throw new BrandNotFoundException(ErrorCode.E0501.getCode(), id.toString());
        }

        BrandFindResponseDto brandFindResponesDto = brandMapper.brandToBrandFindResponesDto(optionalBrand.get());

        log.info("BrandService:findByPage traceId: {}, response: {}", traceId, brandFindResponesDto);
        log.info("BrandService:findByPage execution ended.");

        return brandFindResponesDto;
    }

    @Override
    public BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto) {
        if (isNameExist(brandCreateRequestDto.getName())) {
            throw new BrandNameAlreadyExistException(ErrorCode.E0505.getCode(), brandCreateRequestDto.getName());
        }

        return brandMapper.brandToBrandCreateResponseDto(
                brandRepository.save(brandMapper.brandCreateRequestDtoToBrand(brandCreateRequestDto))
        );

    }

    @Override
    public BrandUpdateResponseDto update(final UUID id, BrandUpdateRequestDto brandUpdateRequestDto)
            throws BrandNotFoundException {
        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if (!optionalBrand.isPresent()) {
            throw new BrandNotFoundException(ErrorCode.E0502.getCode(), id.toString());
        }

        Brand brand = brandMapper.brandUpdateRequestDtoToBrand(brandUpdateRequestDto);
        brand.setId(optionalBrand.get().getId());

        return brandMapper.brandToBrandUpdateResponseDto(brandRepository.save(brand));
    }

    @Override
    public void deleteById(UUID id) throws BrandNotFoundException {
        Long brandCountById = brandRepository.countById(id);
        if (brandCountById == 0) {
            throw new BrandNotFoundException(ErrorCode.E0503.getCode(), id.toString());
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
    public String updateImageName(UUID brandId, String fileName) {
        Optional<Brand> optionalBrand = brandRepository.findById(brandId);

        if (!optionalBrand.isPresent()) {
            throw new BrandNotFoundException(ErrorCode.E0504.getCode(), brandId.toString());
        }

        Brand brand = optionalBrand.get();
        brand.setImageName(fileName);

        Brand brandUpdated = brandRepository.save(brand);
        return brandUpdated.getImageName();
    }

}
