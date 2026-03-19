package com.pm.spring.ema.category.service.impl;

import com.pm.spring.ema.category.mapper.CategoryMapper;
import com.pm.spring.ema.category.model.Category;
import com.pm.spring.ema.category.repository.CategoryRepository;
import com.pm.spring.ema.category.repository.SubCategoryRepository;
import com.pm.spring.ema.category.service.CategoryService;
import com.pm.spring.ema.category.utils.TraceIdHolder;
import com.pm.spring.ema.common.util.dto.CategoryDto;
import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.common.util.exception.utils.ErrorMessage;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

  private static final int CATEGORY_PER_PAGE = 5;
  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;
  private final SubCategoryRepository subCategoryRepository;

  public List<CategoryDto> getAllCategory() {
    log.debug(
        "CategoryService:getAllCategory EXECUTION STARTED. traceId: {}",
        TraceIdHolder.getTraceId());

    List<CategoryDto> categoryFindResponseDtoList =
        categoryRepository.findAll().stream().map(categoryMapper::toCategoryDto).toList();
    log.debug(
        "CategoryService:getAllCategory EXECUTION ENDED. traceId: {}, response {} ",
        TraceIdHolder.getTraceId(),
        categoryFindResponseDtoList);

    return categoryFindResponseDtoList;
  }

  @Override
  public CategoryDto getCategoryById(UUID id) throws NotFoundException {

    log.debug(
        "CategoryService:getCategoryById EXECUTION STARTED. traceId: {}, id: {}",
        TraceIdHolder.getTraceId(),
        id);

    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (optionalCategory.isEmpty()) {
      log.error(
          "CategoryService:getCategoryById EXECUTION ENDED. traceId: {}, errorMessage: Category Not found",
          TraceIdHolder.getTraceId());
      throw new NotFoundException(ErrorCodes.E1502, id.toString());
    }
    CategoryDto categoryFindResponseDto = categoryMapper.toCategoryDto(optionalCategory.get());

    log.debug(
        "CategoryService:getCategoryById EXECUTION ENDED. traceId: {}, response: {}",
        TraceIdHolder.getTraceId(),
        categoryFindResponseDto);

    return categoryFindResponseDto;
  }

  @Override
  public CategoryDto createCategory(CategoryDto categoryRequestDto) throws FoundException {

    log.debug(
        "CategoryService:createCategory EXECUTION STARTED. traceId: {} , brandCreateRequestDto: {}",
        TraceIdHolder.getTraceId(),
        categoryRequestDto);

    if (isCategoryExistByName(categoryRequestDto.getName())) {
      log.error(
          "CategoryService:createCategory EXECUTION ENDED. traceId: {}, errorMessage: {}",
          TraceIdHolder.getTraceId(),
          ErrorMessage.message(ErrorCodes.E1501, categoryRequestDto.getName()));
      throw new FoundException(ErrorCodes.E1501, categoryRequestDto.getName());
    }
    Category category = categoryMapper.toCategory(categoryRequestDto);
    Category savedCategory = categoryRepository.save(category);
    CategoryDto categoryResponseDto = categoryMapper.toCategoryDto(savedCategory);

    log.debug(
        "CategoryService:createCategory EXECUTION ENDED. traceId: {}, response: {}",
        TraceIdHolder.getTraceId(),
        categoryRequestDto);
    return categoryResponseDto;
  }

  @Override
  public CategoryDto updateCategory(UUID id, CategoryDto CategoryRequestDto)
      throws NotFoundException {

    log.debug(
        "CategoryService:updateCategory EXECUTION STARTED. traceId: {}, id: {}, brandCreateRequestDto: {}",
        TraceIdHolder.getTraceId(),
        id,
        CategoryRequestDto);

    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (optionalCategory.isEmpty()) {
      throw new NotFoundException(ErrorCodes.E1503, id.toString());
    }

    Category category = categoryMapper.toCategory(CategoryRequestDto);
    category.setId(optionalCategory.get().getId());
    Category updatedCategory = categoryRepository.save(category);
    CategoryDto categoryUpdateResponseDto = categoryMapper.toCategoryDto(updatedCategory);

    log.debug(
        "CategoryService:updateCategory EXECUTION ENDED. traceId: {}, response: {}",
        TraceIdHolder.getTraceId(),
        categoryUpdateResponseDto);

    return categoryUpdateResponseDto;
  }

  @Override
  public CategoryDto deleteCategoryById(UUID id) throws NotFoundException {

    log.debug(
        "CategoryService:deleteCategoryById EXECUTION STARTED. traceId: {}, id: {}",
        TraceIdHolder.getTraceId(),
        id);

    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (optionalCategory.isPresent()) {

      CategoryDto categoryDeleteResponseDto = categoryMapper.toCategoryDto(optionalCategory.get());

      Category category = optionalCategory.get();

      subCategoryRepository.deleteAll(subCategoryRepository.findByCategory(category));
      categoryRepository.deleteById(category.getId());

      log.debug(
          "CategoryService:deleteCategoryById EXECUTION ENDED. response : {}",
          categoryDeleteResponseDto);

      return categoryDeleteResponseDto;
    } else {
      log.error(
          "CategoryService:deleteCategoryById EXECUTION ENDED. traceId: {}, errorMessage: {}",
          TraceIdHolder.getTraceId(),
          ErrorMessage.message(ErrorCodes.E1504, id.toString()));
      throw new NotFoundException(ErrorCodes.E1504, id.toString());
    }
  }

  @Override
  public Boolean isCategoryExistByName(String name) {

    log.debug(
        "CategoryService:isCategoryExistByName EXECUTION STARTED. traceId: {}",
        TraceIdHolder.getTraceId());

    Optional<Category> optionalParentCategory = categoryRepository.findByName(name);
    if (optionalParentCategory.isPresent()) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  public Boolean isCategoryExistById(UUID id) {

    log.debug(
        "CategoryService:isCategoryExistById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (optionalCategory.isPresent()) {
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }

  @Override
  public List<CategoryDto> findByPage(
      int pageNumber, String sortField, String sortDir, String keyword) {
    Sort sort = Sort.by(sortField);
    sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

    Pageable pageable = PageRequest.of(pageNumber - 1, CATEGORY_PER_PAGE, sort);

    if (keyword != null) {
      return categoryRepository.findAllByPage(keyword, pageable).stream()
          .map(categoryMapper::toCategoryDto)
          .toList();
    }
    return categoryRepository.findAll().stream().map(categoryMapper::toCategoryDto).toList();
  }

  @Override
  public String updateImageById(UUID id, String fileName) throws NotFoundException {

    log.debug(
        "CategoryService:updateImageById EXECUTION STARTED. traceId: {}, brandId:{}, fileName: {}",
        TraceIdHolder.getTraceId(),
        id,
        fileName);

    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (optionalCategory.isEmpty()) {
      log.error(
          "CategoryService:updateImageById EXECUTION ENDED. traceId: {}, errorMessage:{}",
          TraceIdHolder.getTraceId(),
          ErrorMessage.message(ErrorCodes.E1505, id.toString()));
      throw new NotFoundException(ErrorCodes.E1505, id.toString());
    }
    Category category = optionalCategory.get();
    category.setImageName(fileName);
    Category updatedCategory = categoryRepository.save(category);

    log.debug(
        "CategoryService:updateImageById EXECUTION ENDED. traceId: {}, updatedImageName: {}",
        TraceIdHolder.getTraceId(),
        updatedCategory.getImageName());

    return updatedCategory.getImageName();
  }

  @Override
  public String getCategoryImageNameById(UUID id) {

    log.debug(
        "CategoryService:getCategoryImageNameById EXECUTION STARTED. traceId: {}, id: {}",
        TraceIdHolder.getTraceId(),
        id);

    Optional<Category> optionalCategory = categoryRepository.findById(id);

    if (optionalCategory.isEmpty()) {
      log.error(
          "BrandService:getCategoryImageNameById EXECUTION ENDED. traceId: {}, errorMessage: Brand Not found",
          TraceIdHolder.getTraceId());
      throw new NotFoundException(ErrorCodes.E1506, id.toString());
    }

    String imageName = optionalCategory.get().getImageName();

    log.debug(
        "CategoryService:getCategoryImageNameById EXECUTION ENDED. traceId: {}, response: {}",
        TraceIdHolder.getTraceId(),
        imageName);
    return imageName;
  }
}
