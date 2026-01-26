package com.pm.spring.ema.brand.service;

import com.pm.spring.ema.brand.dto.BrandDto;
import com.pm.spring.ema.brand.model.dao.BrandDao;
import com.pm.spring.ema.brand.model.entity.Brand;
import com.pm.spring.ema.brand.mapper.BrandMapper;
import com.pm.spring.ema.common.util.dto.BrandFindResponseDto;
import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
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

        List<BrandFindResponseDto> brandFindResponseDtoList = brandDao.findAll()
                .stream()
                .map(brandMapper::toBrandFindResponseDto)
                .toList();

        log.info("BrandService:getAllBrands execution ended.");

        return brandFindResponseDtoList;
    }

    public List<BrandFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDirectory, String searchField, String searchKeyword) {
        log.info("BrandService:getBrandsByPage execution started.");

        if (sortField.isBlank()) {
            sortField = "name";

        }
        Sort sort = Sort.by(sortField);
        sort = sortDirectory.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);

        Page<Brand> brandList;

        if (searchKeyword != null && searchField.equals("BRAND_NAME")) {
            brandList =  brandDao.findAllByName(searchKeyword, pageable);

        } else {
            brandList =  brandDao.findAll(pageable);
        }

        List<BrandFindResponseDto> brandFindResponseDtoList = brandList.stream()
                .map(brandMapper::toBrandFindResponseDto)
                .toList();
        log.info("BrandService:getBrandsByPage execution ended.");

        return brandFindResponseDtoList;

    }

    @Override
    public BrandFindResponseDto getById(UUID id) throws NotFoundException {
        log.info("BrandService:getBrandById execution started.");

        Optional<Brand> optionalBrand = brandDao.findById(id);

        if (optionalBrand.isEmpty()) {
            log.info("BrandService:getBrandById execution ended.");
            throw new NotFoundException(ErrorCodes.E0501, id.toString());
        }

        BrandFindResponseDto brandFindResponseDto = brandMapper.toBrandFindResponseDto(optionalBrand.get());
        log.info("BrandService:getBrandById execution ended.");

        return brandFindResponseDto;
    }

    @Override
    public BrandDto create(BrandDto brandDto) {
        log.info("BrandService:createBrand execution started.");

        if (isNameExist(brandDto.getName())) {
            throw new FoundException(ErrorCodes.E0502, brandDto.getName());
        }

        Brand brand = brandMapper.toBrand(brandDto);
        Brand brandCreated = brandDao.save(brand);
        BrandDto brandCreateResponseDto = brandMapper.toBrandDto(brandCreated);

        log.info("BrandService:createBrand execution ended.");

        return brandCreateResponseDto;

    }

    @Override
    public BrandDto update(final UUID id, BrandDto brandUpdateRequestDto)
            throws NotFoundException {
        log.info("BrandService:updateBrand execution started.");


        Optional<Brand> optionalBrand = brandDao.findById(id);

        if (optionalBrand.isEmpty()) {
            throw new NotFoundException(ErrorCodes.E0502, id.toString());
        }

        Brand brand = brandMapper.toBrand(brandUpdateRequestDto);
        brand.setId(optionalBrand.get().getId());

        Brand brandUpdated = brandDao.save(brand);
        BrandDto brandUpdateResponseDto = brandMapper.toBrandDto(brandUpdated);

        log.info("BrandService:updateBrand execution ended.");

        return brandUpdateResponseDto;
    }

    @Override
    public void deleteById(UUID id) throws NotFoundException {
        log.info("BrandService:deleteBrandById execution started.");

        Long brandCountById = brandDao.countById(id);
        if (brandCountById == 0) {
            throw new NotFoundException(ErrorCodes.E0501, id.toString());
        }

        brandDao.deleteById(id);
        log.info("BrandService:deleteBrandById execution ended.");

    }

    @Override
    public String updateImageName(UUID brandId, String fileName) {
        log.info("BrandService:updateImageName execution started.");

        Optional<Brand> optionalBrand = brandDao.findById(brandId);

        if (optionalBrand.isEmpty()) {
            throw new NotFoundException(ErrorCodes.E0501, brandId.toString());
        }

        Brand brand = optionalBrand.get();
        brand.setImageName(fileName);

        Brand brandUpdated = brandDao.save(brand);

        log.info("BrandService:updateImageName execution ended.");

        return brandUpdated.getImageName();
    }

    @Override
    public String getImageNameById(UUID id) {
        log.info("BrandService:getBrandImageNameById execution started.");

        Optional<Brand> optionalBrand = brandDao.findById(id);

        if (optionalBrand.isEmpty()) {
            log.info("BrandService:getBrandImageNameById execution ended.");
            throw new NotFoundException(ErrorCodes.E0501, id.toString());
        }

        String imageName =  optionalBrand.get().getImageName();

        log.info("BrandService:getBrandImageNameById execution ended.");

        return imageName;
    }

    @Override
    public Boolean isIdExist(UUID uuid) {

        Optional<Brand> optionalBrand = brandDao.findById(uuid);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean isNameExist(String name) {

        Optional<Brand> optionalBrand = brandDao.findByName(name);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
