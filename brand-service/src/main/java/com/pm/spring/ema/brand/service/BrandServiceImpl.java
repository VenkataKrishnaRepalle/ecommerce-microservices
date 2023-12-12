package com.pm.spring.ema.brand.service;

import com.pm.spring.ema.brand.dto.request.BrandCreateRequestDto;
import com.pm.spring.ema.brand.dto.response.BrandCreateResponseDto;
import com.pm.spring.ema.brand.dto.request.BrandUpdateRequestDto;
import com.pm.spring.ema.brand.dto.response.BrandUpdateResponseDto;
import com.pm.spring.ema.brand.model.dao.BrandDao;
import com.pm.spring.ema.brand.model.entity.Brand;
import com.pm.spring.ema.brand.dto.enums.BrandSearchKeywordEnum;
import com.pm.spring.ema.brand.exception.BrandNameAlreadyExistException;
import com.pm.spring.ema.brand.exception.BrandNotFoundException;
import com.pm.spring.ema.brand.dto.mapper.BrandMapper;
import com.pm.spring.ema.brand.utils.TraceIdHolder;
import com.pm.spring.ema.common.dto.BrandFindResponseDto;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
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

    private final BrandDao brandDao;
    private final BrandMapper brandMapper;

    public List<BrandFindResponseDto> getAll() {
        log.info("BrandService:getAllBrands execution started.");
        log.debug("BrandService:getAllBrands traceId: {}", TraceIdHolder.getTraceId());

        List<BrandFindResponseDto> brandFindResponseDtoList = brandDao.findAll()
                .stream()
                .map(brandMapper::brandToBrandFindResponseDto)
                .toList();

        log.debug("BrandService:getAllBrands traceId: {}, response {} ", TraceIdHolder.getTraceId(), brandFindResponseDtoList);
        log.info("BrandService:getAllBrands execution ended.");

        return brandFindResponseDtoList;
    }

    public List<BrandFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDirectory, BrandSearchKeywordEnum searchField, String searchKeyword) {
        log.info("BrandService:getBrandsByPage execution started.");
        log.debug("BrandService:getBrandsByPage traceId: {},  pageNumber: {}, perPageCount: {}, sortField: {}, sortDirectory: {}, searchField: {}, searchKeyword: {}", TraceIdHolder.getTraceId(), pageNumber, perPageCount, sortField, sortDirectory, searchField, searchKeyword);

        if (sortField.isBlank()) {
            sortField = "name";

        }
        Sort sort = Sort.by(sortField);
        sort = sortDirectory.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);

        Page<Brand> brandList;

        if (searchKeyword != null && searchField.equals(BrandSearchKeywordEnum.BRAND_NAME)) {
            brandList =  brandDao.findAllByName(searchKeyword, pageable);

        } else {
            brandList =  brandDao.findAll(pageable);
        }

        List<BrandFindResponseDto> brandFindResponseDtoList = brandList.stream()
                .map(brandMapper::brandToBrandFindResponseDto)
                .toList();

        log.debug("BrandService:getBrandsByPage traceId: {}", TraceIdHolder.getTraceId());
        log.info("BrandService:getBrandsByPage execution ended.");

        return brandFindResponseDtoList;

    }

    @Override
    public BrandFindResponseDto getById(UUID id) throws BrandNotFoundException {
        log.info("BrandService:getBrandById execution started.");
        log.debug("BrandService:getBrandById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Brand> optionalBrand = brandDao.findById(id);

        if (!optionalBrand.isPresent()) {
            log.error("BrandService:getBrandById traceId: {}, errorMessage: Brand Not found", TraceIdHolder.getTraceId());
            log.info("BrandService:getBrandById execution ended.");
            throw new BrandNotFoundException(ErrorCodes.E0501, id.toString());
        }

        BrandFindResponseDto brandFindResponseDto = brandMapper.brandToBrandFindResponseDto(optionalBrand.get());

        log.debug("BrandService:getBrandById traceId: {}, response: {}", TraceIdHolder.getTraceId(), brandFindResponseDto);
        log.info("BrandService:getBrandById execution ended.");

        return brandFindResponseDto;
    }

    @Override
    public BrandCreateResponseDto create(BrandCreateRequestDto brandCreateRequestDto) {
        log.info("BrandService:createBrand execution started.");
        log.debug("BrandService:createBrand traceId: {} , brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), brandCreateRequestDto);

        if (isNameExist(brandCreateRequestDto.getName())) {
            log.error("BrandService:createBrand traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E0506, brandCreateRequestDto.getName()));
            throw new BrandNameAlreadyExistException(ErrorCodes.E0506, brandCreateRequestDto.getName());
        }

        Brand brand = brandMapper.brandCreateRequestDtoToBrand(brandCreateRequestDto);
        Brand brandCreated = brandDao.save(brand);
        BrandCreateResponseDto brandCreateResponseDto = brandMapper.brandToBrandCreateResponseDto(brandCreated);

        log.debug("BrandService:createBrand traceId: {}, response: {}", TraceIdHolder.getTraceId(), brandCreateResponseDto);
        log.info("BrandService:createBrand execution ended.");

        return brandCreateResponseDto;

    }

    @Override
    public BrandUpdateResponseDto update(final UUID id, BrandUpdateRequestDto brandUpdateRequestDto)
            throws BrandNotFoundException {
        log.info("BrandService:updateBrand execution started.");
        log.debug("BrandService:updateBrand traceId: {}, id: {}, brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), id, brandUpdateRequestDto);


        Optional<Brand> optionalBrand = brandDao.findById(id);

        if (!optionalBrand.isPresent()) {
            throw new BrandNotFoundException(ErrorCodes.E0502, id.toString());
        }

        Brand brand = brandMapper.brandUpdateRequestDtoToBrand(brandUpdateRequestDto);
        brand.setId(optionalBrand.get().getId());

        Brand brandUpdated = brandDao.save(brand);
        BrandUpdateResponseDto brandUpdateResponseDto = brandMapper.brandToBrandUpdateResponseDto(brandUpdated);

        log.debug("BrandService:updateBrand traceId: {}, response: {}", TraceIdHolder.getTraceId(), brandUpdateResponseDto);
        log.info("BrandService:updateBrand execution ended.");

        return brandUpdateResponseDto;
    }

    @Override
    public void deleteById(UUID id) throws BrandNotFoundException {
        log.info("BrandService:deleteBrandById execution started.");
        log.debug("BrandService:deleteBrandById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Long brandCountById = brandDao.countById(id);
        if (brandCountById == 0) {
            log.error("BrandService:deleteBrandById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E0503, id.toString()));
            throw new BrandNotFoundException(ErrorCodes.E0503, id.toString());
        }

        brandDao.deleteById(id);
        log.info("BrandService:deleteBrandById execution ended.");

    }

    @Override
    public String updateImageName(UUID brandId, String fileName) {
        log.info("BrandService:updateImageName execution started.");
        log.debug("BrandService:updateImageName traceId: {}, brandId:{}, fileName: {}", TraceIdHolder.getTraceId(), brandId, fileName);

        Optional<Brand> optionalBrand = brandDao.findById(brandId);

        if (!optionalBrand.isPresent()) {
            log.error("BrandService:updateImageName traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E0504, brandId.toString()));
            throw new BrandNotFoundException(ErrorCodes.E0504, brandId.toString());
        }

        Brand brand = optionalBrand.get();
        brand.setImageName(fileName);

        Brand brandUpdated = brandDao.save(brand);

        log.debug("BrandService:updateImageName traceId: {}, updatedImageName: {}", TraceIdHolder.getTraceId(), brandUpdated.getImageName());
        log.info("BrandService:updateImageName execution ended.");

        return brandUpdated.getImageName();
    }

    @Override
    public String getImageNameById(UUID id) {
        log.info("BrandService:getBrandImageNameById execution started.");
        log.debug("BrandService:getBrandImageNameById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Brand> optionalBrand = brandDao.findById(id);

        if (!optionalBrand.isPresent()) {
            log.error("BrandService:getBrandImageNameById traceId: {}, errorMessage: Brand Not found", TraceIdHolder.getTraceId());
            log.info("BrandService:getBrandImageNameById execution ended.");
            throw new BrandNotFoundException(ErrorCodes.E0508, id.toString());
        }

        String imageName =  optionalBrand.get().getImageName();

        log.debug("BrandService:getBrandImageNameById traceId: {}, response: {}", TraceIdHolder.getTraceId(), imageName);
        log.info("BrandService:getBrandImageNameById execution ended.");

        return imageName;
    }

    @Override
    public Boolean isIdExist(UUID uuid) {
        log.info("BrandService:isIdExist execution started. traceId: {}", TraceIdHolder.getTraceId());
        log.debug("BrandService:isIdExist traceId: {}, id: {}", TraceIdHolder.getTraceId(), uuid);

        Optional<Brand> optionalBrand = brandDao.findById(uuid);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean isNameExist(String name) {
        log.info("BrandService:isNameExist execution started. traceId: {}", TraceIdHolder.getTraceId());

        Optional<Brand> optionalBrand = brandDao.findByName(name);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
