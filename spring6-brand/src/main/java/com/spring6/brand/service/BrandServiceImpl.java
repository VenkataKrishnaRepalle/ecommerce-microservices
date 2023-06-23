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
import com.spring6.common.exeption.ErrorMessage;
import com.spring6.common.utils.GlobalConstants;
import com.spring6.common.utils.TraceIdHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public List<BrandFindResponseDto> findAll() {
        log.info("BrandService:findAll execution started.");
        log.debug("BrandService:findAll traceId: {}", TraceIdHolder.traceId);

        List<BrandFindResponseDto> brandFindResponseDtoList = brandRepository.findAll()
                .stream()
                .map(brandMapper::brandToBrandFindResponesDto)
                .toList();

        log.debug("BrandService:findAll traceId: {}, response {} ", TraceIdHolder.traceId, brandFindResponseDtoList);
        log.info("BrandService:findAll execution ended.");

        return brandFindResponseDtoList;
    }

    public List<BrandFindResponseDto> findByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDirectory, BrandSearchKeywordEnum searchField, String searchKeyword) {
        log.info("BrandService:findByPage execution started.");
        log.debug("BrandService:findByPage traceId: {},  pageNumber: {}, perPageCount: {}, sortField: {}, sortDirectory: {}, searchField: {}, searchKeyword: {}", TraceIdHolder.traceId, pageNumber, perPageCount, sortField, sortDirectory, searchField, searchKeyword);

        if (sortField.isBlank()) {
            sortField = "name";

        }
        Sort sort = Sort.by(sortField);
        sort = sortDirectory.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);

        Page<Brand> brandList;

        if (searchKeyword != null && searchField.equals(BrandSearchKeywordEnum.BRAND_NAME)) {
            brandList =  brandRepository.findAllByName(searchKeyword, pageable);

        } else {
            brandList =  brandRepository.findAll(pageable);
        }

        List<BrandFindResponseDto> brandFindResponseDtoList = brandList.stream()
                .map(brandMapper::brandToBrandFindResponesDto)
                .toList();

        log.debug("BrandService:findByPage traceId: {}", TraceIdHolder.traceId);
        log.info("BrandService:findByPage execution ended.");

        return brandFindResponseDtoList;

    }

    @Override
    public BrandFindResponseDto findById(UUID id) throws BrandNotFoundException {
        log.info("BrandService:findById execution started.");
        log.debug("BrandService:findById traceId: {}, id: {}", TraceIdHolder.traceId, id);

        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if (!optionalBrand.isPresent()) {
            log.error("BrandService:findByPage traceId: {}, errorMessage: Brand Not found", TraceIdHolder.traceId);
            log.info("BrandService:findByPage execution ended.");
            throw new BrandNotFoundException(ErrorCode.E0501.getCode(), id.toString());
        }

        BrandFindResponseDto brandFindResponesDto = brandMapper.brandToBrandFindResponesDto(optionalBrand.get());

        log.debug("BrandService:findByPage traceId: {}, response: {}", TraceIdHolder.traceId, brandFindResponesDto);
        log.info("BrandService:findByPage execution ended.");

        return brandFindResponesDto;
    }

    @Override
    public BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto) {
        log.info("BrandService:create execution started.");
        log.debug("BrandService:create traceId: {} , brandCreateRequestDto: {}", TraceIdHolder.traceId, brandCreateRequestDto);

        if (isNameExist(brandCreateRequestDto.getName())) {
            log.error("BrandService:create traceId: {}, errorMessage: {}", TraceIdHolder.traceId, ErrorMessage.message(ErrorCode.E0506.getCode(), brandCreateRequestDto.getName()));
            throw new BrandNameAlreadyExistException(ErrorCode.E0506.getCode(), brandCreateRequestDto.getName());
        }

        Brand brand = brandMapper.brandCreateRequestDtoToBrand(brandCreateRequestDto);
        Brand brandCreated = brandRepository.save(brand);
        BrandCreateResponseDto brandCreateResponseDto = brandMapper.brandToBrandCreateResponseDto(brandCreated);

        log.debug("BrandService:create traceId: {}, response: {}", TraceIdHolder.traceId, brandCreateResponseDto);
        log.info("BrandService:create execution ended.");

        return brandCreateResponseDto;

    }

    @Override
    public BrandUpdateResponseDto update(final UUID id, BrandUpdateRequestDto brandUpdateRequestDto)
            throws BrandNotFoundException {
        log.info("BrandService:update execution started.");
        log.debug("BrandService:create traceId: {}, id: {}, brandCreateRequestDto: {}", TraceIdHolder.traceId, id, brandUpdateRequestDto);


        Optional<Brand> optionalBrand = brandRepository.findById(id);

        if (!optionalBrand.isPresent()) {
            throw new BrandNotFoundException(ErrorCode.E0502.getCode(), id.toString());
        }

        Brand brand = brandMapper.brandUpdateRequestDtoToBrand(brandUpdateRequestDto);
        brand.setId(optionalBrand.get().getId());

        Brand brandUpdated = brandRepository.save(brand);
        BrandUpdateResponseDto brandUpdateResponseDto = brandMapper.brandToBrandUpdateResponseDto(brandUpdated);

        log.debug("BrandService:update traceId: {}, response: {}", TraceIdHolder.traceId, brandUpdateResponseDto);
        log.info("BrandService:update execution ended.");

        return brandUpdateResponseDto;
    }

    @Override
    public void deleteById(UUID id) throws BrandNotFoundException {
        log.info("BrandService:deleteById execution started.");
        log.debug("BrandService:deleteById traceId: {}, id: {}", TraceIdHolder.traceId, id);

        Long brandCountById = brandRepository.countById(id);
        if (brandCountById == 0) {
            log.error("BrandService:deleteById traceId: {}, errorMessage: {}", TraceIdHolder.traceId, ErrorMessage.message(ErrorCode.E0503.getCode(), id.toString()));
            throw new BrandNotFoundException(ErrorCode.E0503.getCode(), id.toString());
        }

        brandRepository.deleteById(id);
        log.info("BrandService:deleteById execution ended.");

    }

    @Override
    public String updateImageName(UUID brandId, String fileName) {
        log.info("BrandService:updateImageName execution started.");
        log.debug("BrandService:updateImageName traceId: {}, brandId:{}, fileName: {}", TraceIdHolder.traceId, brandId, fileName);

        Optional<Brand> optionalBrand = brandRepository.findById(brandId);

        if (!optionalBrand.isPresent()) {
            log.error("BrandService:updateImageName traceId: {}, errorMessage:{}", TraceIdHolder.traceId, ErrorMessage.message(ErrorCode.E0504.getCode(), brandId.toString()));
            throw new BrandNotFoundException(ErrorCode.E0504.getCode(), brandId.toString());
        }

        Brand brand = optionalBrand.get();
        brand.setImageName(fileName);

        Brand brandUpdated = brandRepository.save(brand);

        log.debug("BrandService:updateImageName traceId: {}, updatedImageName: {}", TraceIdHolder.traceId, brandUpdated.getImageName());
        log.info("BrandService:updateImageName execution ended.");

        return brandUpdated.getImageName();
    }
    @Override
    public Boolean isNameExist(String name) {
        log.info("BrandService:isNameExist execution started. traceId: {}", TraceIdHolder.traceId);

        Optional<Brand> optionalBrand = brandRepository.findByName(name);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isIdExist(UUID uuid) {
        log.info("BrandService:isIdExist execution started. traceId: {}", TraceIdHolder.traceId);
        log.debug("BrandService:isIdExist traceId: {}, id: {}", TraceIdHolder.traceId, uuid);

        Optional<Brand> optionalBrand = brandRepository.findById(uuid);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }



}
