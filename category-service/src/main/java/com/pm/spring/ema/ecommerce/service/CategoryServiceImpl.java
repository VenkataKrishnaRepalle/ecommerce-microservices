package com.pm.spring.ema.ecommerce.service;

import com.pm.spring.ema.common.dto.CategoryFindResponseDto;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import com.pm.spring.ema.ecommerce.dto.CategoryCreateRequestDto;
import com.pm.spring.ema.ecommerce.dto.CategoryCreateResponseDto;
import com.pm.spring.ema.ecommerce.dto.CategoryUpdateRequestDto;
import com.pm.spring.ema.ecommerce.dto.CategoryUpdateResponseDto;
import com.pm.spring.ema.ecommerce.model.entity.Category;
import com.pm.spring.ema.ecommerce.model.entity.SubCategory;
import com.pm.spring.ema.ecommerce.exception.CategoryNameAlreadyExistException;
import com.pm.spring.ema.ecommerce.exception.CategoryNotFoundException;
import com.pm.spring.ema.ecommerce.mapper.CategoryMapper;
import com.pm.spring.ema.ecommerce.model.repository.SubCategoryRepository;
import com.pm.spring.ema.ecommerce.model.repository.CategoryRepository;
import com.pm.spring.ema.ecommerce.utils.TraceIdHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private static final int CATEGORY_PER_PAGE = 5;
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final SubCategoryRepository subCategoryRepository;


    public List<CategoryFindResponseDto> getAllCategory() {
        log.info("CategoryService:getAllCategory execution started.");
        log.debug("CategoryService:getAllCategory traceId: {}", TraceIdHolder.getTraceId());


        List<CategoryFindResponseDto> categoryFindResponseDtoList = categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryFindResponseDto)
                .toList();
        log.debug("CategoryService:getAllCategory traceId: {}, response {} ", TraceIdHolder.getTraceId(), categoryFindResponseDtoList);
        log.info("CategoryService:getAllCategory execution ended.");

        return categoryFindResponseDtoList;
    }

    @Override
    public CategoryFindResponseDto getCategoryById(UUID id) throws CategoryNotFoundException {

        log.info("CategoryService:getCategoryById execution started.");
        log.debug("CategoryService:getCategoryById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            log.error("CategoryService:getCategoryById traceId: {}, errorMessage: Brand Not found", TraceIdHolder.getTraceId());
            log.info("CategoryService:getCategoryById execution ended.");
            throw new CategoryNotFoundException(ErrorCodes.E1502, id.toString());
        }
        CategoryFindResponseDto categoryFindResponseDto = categoryMapper.categoryToCategoryFindResponseDto(optionalCategory.get());

        log.debug("CategoryService:getCategoryById traceId: {}, response: {}", TraceIdHolder.getTraceId(), categoryFindResponseDto);
        log.info("CategoryService:getCategoryById execution ended.");

        return categoryFindResponseDto;

    }

    @Override
    public CategoryCreateResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto) throws CategoryNameAlreadyExistException{

        log.info("CategoryService:createCategory execution started.");
        log.debug("CategoryService:createCategory traceId: {} , brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), categoryCreateRequestDto);

        if (isCategoryExistByName(categoryCreateRequestDto.getName())) {
            log.error("CategoryService:createCategory traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1501, categoryCreateRequestDto.getName()));
            throw new CategoryNameAlreadyExistException(ErrorCodes.E1501, categoryCreateRequestDto.getName());
        }
        Category category = categoryMapper.categoryCreateRequestDtoToCategory(categoryCreateRequestDto);
        Category savedCategory = categoryRepository.save(category);
        CategoryCreateResponseDto categoryCreateResponseDto = categoryMapper.categoryToCategoryCreateResponseDto(savedCategory);

        log.debug("CategoryService:createCategory traceId: {}, response: {}", TraceIdHolder.getTraceId(), categoryCreateRequestDto);
        log.info("CategoryService:createCategory execution ended.");
        return categoryCreateResponseDto;

    }

    @Override
    public CategoryUpdateResponseDto updateCategory(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto) throws CategoryNotFoundException {
        log.info("CategoryService:updateCategory execution started.");
        log.debug("CategoryService:updateCategory traceId: {}, id: {}, brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), id, categoryUpdateRequestDto);

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new CategoryNotFoundException(ErrorCodes.E1503, id.toString());
        }


        Category category = categoryMapper.categoryUpdateRequestDtoToCategory(categoryUpdateRequestDto);
        category.setId(optionalCategory.get().getId());
        Category updatedCategory = categoryRepository.save(category);
        CategoryUpdateResponseDto categoryUpdateResponseDto = categoryMapper.categoryToCategoryUpdateResponseDto(updatedCategory);

        log.debug("CategoryService:updateCategory traceId: {}, response: {}", TraceIdHolder.getTraceId(), categoryUpdateResponseDto);
        log.info("CategoryService:updateCategory execution ended.");
        return categoryUpdateResponseDto;
    }

    @Override
    public void deleteCategoryById(UUID id) throws CategoryNotFoundException {
        log.info("CategoryService:deleteCategoryById execution started.");
        log.debug("CategoryService:deleteCategoryById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);


        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            log.error("CategoryService:deleteCategoryById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1504, id.toString()));

            throw new CategoryNotFoundException(ErrorCodes.E1504, id.toString());
        }
        Category category = optionalCategory.get();
        for (SubCategory subCategory : subCategoryRepository.findByCategory(category)) {
            subCategoryRepository.delete(subCategory);
        }
        categoryRepository.deleteById(category.getId());
        log.info("CategoryService:deleteCategoryById execution ended.");

    }

    @Override
    public Boolean isCategoryExistByName(String name) {
        log.info("CategoryService:isCategoryExistByName execution started. traceId: {}", TraceIdHolder.getTraceId());

        Optional<Category> optionalParentCategory = categoryRepository.findByName(name);
        if (optionalParentCategory.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isCategoryExistById(UUID id) {
        log.info("CategoryService:isCategoryExistById execution started. traceId: {}", TraceIdHolder.getTraceId());
        log.debug("CategoryService:isCategoryExistById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public List<CategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, CATEGORY_PER_PAGE, sort);

        if (keyword != null) {
            return categoryRepository.findByPage(keyword, (java.awt.print.Pageable) pageable)
                    .stream()
                    .map(categoryMapper::categoryToCategoryFindResponseDto)
                    .toList();
        }
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryFindResponseDto)
                .toList();

    }

    @Override
    public String updateImageById(UUID id, String fileName) throws CategoryNotFoundException{
        log.info("CategoryService:updateImageById execution started.");
        log.debug("CategoryService:updateImageById traceId: {}, brandId:{}, fileName: {}", TraceIdHolder.getTraceId(), id, fileName);

        Optional<Category> optionalCategory = categoryRepository.findById(id);
        if (optionalCategory.isEmpty()) {
            log.error("CategoryService:updateImageById traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1505, id.toString()));
            throw new CategoryNotFoundException(ErrorCodes.E1505, id.toString());
        }
        Category category = optionalCategory.get();
        category.setImage(fileName);
        Category updatedCategory = categoryRepository.save(category);

        log.debug("CategoryService:updateImageById traceId: {}, updatedImageName: {}", TraceIdHolder.getTraceId(), updatedCategory.getImage());
        log.info("CategoryService:updateImageById execution ended.");

        return updatedCategory.getImage();


    }

    @Override
    public String getCategoryImageNameById(UUID id) {
        log.info("CategoryService:getCategoryImageNameById execution started.");
        log.debug("CategoryService:getCategoryImageNameById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Category> optionalCategory = categoryRepository.findById(id);

        if (!optionalCategory.isPresent()) {
            log.error("BrandService:getCategoryImageNameById traceId: {}, errorMessage: Brand Not found", TraceIdHolder.getTraceId());
            log.info("BrandService:getCategoryImageNameById execution ended.");
            throw new CategoryNotFoundException(ErrorCodes.E1506, id.toString());
        }

        String imageName = optionalCategory.get().getImage();

        log.debug("CategoryService:getCategoryImageNameById traceId: {}, response: {}", TraceIdHolder.getTraceId(), imageName);
        log.info("CategoryService:getCategoryImageNameById execution ended.");

        return imageName;
    }


}
