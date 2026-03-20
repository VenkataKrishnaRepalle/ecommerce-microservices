package com.pm.spring.ema.brand.service;

import com.pm.spring.ema.brand.entity.Brand;
import com.pm.spring.ema.brand.feign.CategoryClient;
import com.pm.spring.ema.brand.feign.SubCategoryClient;
import com.pm.spring.ema.brand.mapper.BrandMapper;
import com.pm.spring.ema.brand.repository.BrandRepository;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.dto.BrandDto;
import com.pm.spring.ema.common.util.dto.CategoryDto;
import com.pm.spring.ema.common.util.dto.SubCategoryDto;
import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.InvalidInputException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.common.util.handler.CompletableFutureHandling;
import com.pm.spring.ema.common.util.handler.TokenHandler;
import feign.FeignException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BrandServiceImpl implements BrandService {

  private final BrandRepository brandRepo;

  private final BrandMapper brandMapper;

  private final CategoryClient categoryClient;

  private final SubCategoryClient subCategoryClient;

  private final CompletableFutureHandling completableFutureHandling;

  private final TokenHandler tokenHandler;

  public List<BrandDto> getAll() {
    return brandRepo.findAll().stream().map(brandMapper::toBrandDto).toList();
  }

  public List<BrandDto> getByPage(
      Integer pageNumber,
      Integer perPageCount,
      String sortField,
      String sortDirectory,
      String searchField,
      String searchKeyword) {
    if (sortField.isBlank()) {
      sortField = "name";
    }
    Sort sort = Sort.by(sortField);
    sort = sortDirectory.equals("ASC") ? sort.ascending() : sort.descending();

    Pageable pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);

    Page<Brand> brandList;

    if (searchKeyword != null && searchField.equals("BRAND_NAME")) {
      brandList = brandRepo.findAllByName(searchKeyword, pageable);

    } else {
      brandList = brandRepo.findAll(pageable);
    }

    return brandList.stream().map(brandMapper::toBrandDto).toList();
  }

  @Override
  public BrandDto getById(UUID id) throws NotFoundException {
    Optional<Brand> optionalBrand = brandRepo.findById(id);
    if (optionalBrand.isEmpty()) {
      throw new NotFoundException(ErrorCodes.E0501, id.toString());
    }
    return brandMapper.toBrandDto(optionalBrand.get());
  }

  @Override
  public BrandDto create(BrandDto brandDto) {
    if (isNameExist(brandDto.getName())) {
      throw new FoundException(ErrorCodes.E0502, brandDto.getName());
    }
    validateBrandRequest(brandDto);
    Brand brandCreated = brandRepo.save(brandMapper.toBrand(brandDto));
    return brandMapper.toBrandDto(brandCreated);
  }

  private void validateBrandRequest(BrandDto brandDto) {
    CompletableFuture<CategoryDto> categoryFuture =
        completableFutureHandling.supplyAsyncWithHandling(
            () -> fetchCategoryById(brandDto.getCategoryId()));
    CompletableFuture<SubCategoryDto> subCategoryFuture =
        brandDto.getSubCategoryId() == null
            ? CompletableFuture.completedFuture(null)
            : completableFutureHandling.supplyAsyncWithHandling(
                () -> fetchSubCategoryById(brandDto.getSubCategoryId()));

    try {
      CompletableFuture.allOf(categoryFuture, subCategoryFuture).join();
    } catch (CompletionException ex) {
      Throwable cause = ex.getCause();
      throw cause instanceof RuntimeException
          ? (RuntimeException) cause
          : new RuntimeException(cause);
    }

    CategoryDto categoryDto = categoryFuture.join();
    SubCategoryDto subCategoryDto = subCategoryFuture.join();
    if (subCategoryDto != null && !subCategoryDto.getCategoryUuid().equals(categoryDto.getId())) {
      throw new InvalidInputException("Invalid Input", "Category and subCategory are not linked");
    }
  }

  private CategoryDto fetchCategoryById(UUID categoryId) {
    try {
      return categoryClient.getById(tokenHandler.getToken(), categoryId);
    } catch (FeignException ex) {
      if (ex.status() == Integer.parseInt(HttpStatusCodes.NOT_FOUND)) {
        throw new NotFoundException(ErrorCodes.E1502, categoryId.toString());
      }
      if (ex.status() == Integer.parseInt(HttpStatusCodes.BAD_REQUEST)) {
        throw new InvalidInputException("Invalid Input", "Invalid category id: " + categoryId);
      }
      throw ex;
    }
  }

  private SubCategoryDto fetchSubCategoryById(UUID subCategoryId) {
    try {
      return subCategoryClient.getById(tokenHandler.getToken(), subCategoryId);
    } catch (FeignException ex) {
      if (ex.status() == Integer.parseInt(HttpStatusCodes.NOT_FOUND)) {
        throw new NotFoundException(ErrorCodes.E1509, subCategoryId.toString());
      }
      if (ex.status() == Integer.parseInt(HttpStatusCodes.BAD_REQUEST)) {
        throw new InvalidInputException(
            "Invalid Input", "Invalid subCategory id: " + subCategoryId);
      }
      throw ex;
    }
  }

  @Override
  public BrandDto update(final UUID id, BrandDto brandUpdateRequestDto) {
    Optional<Brand> optionalBrand = brandRepo.findById(id);
    if (optionalBrand.isEmpty()) {
      throw new NotFoundException(ErrorCodes.E0502, id.toString());
    }

    validateBrandRequest(brandUpdateRequestDto);

    Brand brand = brandMapper.toBrand(brandUpdateRequestDto);
    brand.setId(optionalBrand.get().getId());
    Brand brandUpdated = brandRepo.save(brand);
    return brandMapper.toBrandDto(brandUpdated);
  }

  @Override
  public void deleteById(UUID id) throws NotFoundException {
    Long brandCountById = brandRepo.countById(id);
    if (brandCountById == 0) {
      throw new NotFoundException(ErrorCodes.E0501, id.toString());
    }
    brandRepo.deleteById(id);
  }

  @Override
  public String updateImageName(UUID brandId, String fileName) {
    Optional<Brand> optionalBrand = brandRepo.findById(brandId);
    if (optionalBrand.isEmpty()) {
      throw new NotFoundException(ErrorCodes.E0501, brandId.toString());
    }

    Brand brand = optionalBrand.get();
    brand.setImageName(fileName);
    Brand brandUpdated = brandRepo.save(brand);
    return brandUpdated.getImageName();
  }

  @Override
  public String getImageNameById(UUID id) {
    Optional<Brand> optionalBrand = brandRepo.findById(id);
    if (optionalBrand.isEmpty()) {
      throw new NotFoundException(ErrorCodes.E0501, id.toString());
    }
    return optionalBrand.get().getImageName();
  }

  @Override
  public Boolean isIdExist(UUID uuid) {
    Optional<Brand> optionalBrand = brandRepo.findById(uuid);
    if (optionalBrand.isPresent()) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  private Boolean isNameExist(String name) {
    Optional<Brand> optionalBrand = brandRepo.findByName(name);
    if (optionalBrand.isPresent()) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }
}
