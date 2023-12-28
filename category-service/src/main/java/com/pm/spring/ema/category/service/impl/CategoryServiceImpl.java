package com.pm.spring.ema.category.service.impl;

import com.pm.spring.ema.category.common.dto.categoryDto.request.CategoryCreateRequestDto;
import com.pm.spring.ema.category.common.dto.categoryDto.request.CategoryUpdateRequestDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryCreateResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryDeleteResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryFindResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryUpdateResponseDto;
import com.pm.spring.ema.category.model.dao.CategoryDao;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import com.pm.spring.ema.category.model.dao.SubCategoryDao;
import com.pm.spring.ema.category.model.entity.Category;
import com.pm.spring.ema.category.model.entity.SubCategory;
import com.pm.spring.ema.category.exception.CategoryException.CategoryNameAlreadyExistException;
import com.pm.spring.ema.category.exception.CategoryException.CategoryNotFoundException;
import com.pm.spring.ema.category.mapper.CategoryMapper;
import com.pm.spring.ema.category.service.CategoryService;
import com.pm.spring.ema.category.utils.TraceIdHolder;
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
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final int CATEGORY_PER_PAGE = 5;
    private final CategoryDao categoryDao;
    private final CategoryMapper categoryMapper;
    private final SubCategoryDao subCategoryDao;


    public List<CategoryFindResponseDto> getAllCategory() {
        log.debug("CategoryService:getAllCategory EXECUTION STARTED. traceId: {}", TraceIdHolder.getTraceId());


        List<CategoryFindResponseDto> categoryFindResponseDtoList = categoryDao.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryFindResponseDto)
                .toList();
        log.debug("CategoryService:getAllCategory EXECUTION ENDED. traceId: {}, response {} ", TraceIdHolder.getTraceId(), categoryFindResponseDtoList);


        return categoryFindResponseDtoList;
    }

    @Override
    public CategoryFindResponseDto getCategoryById(UUID id) throws CategoryNotFoundException {

        log.debug("CategoryService:getCategoryById EXECUTION STARTED. traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Category> optionalCategory = categoryDao.findById(id);
        if (!optionalCategory.isPresent()) {
            log.error("CategoryService:getCategoryById EXECUTION ENDED. traceId: {}, errorMessage: Category Not found", TraceIdHolder.getTraceId());
            throw new CategoryNotFoundException(ErrorCodes.E1502, id.toString());
        }
        CategoryFindResponseDto categoryFindResponseDto = categoryMapper.categoryToCategoryFindResponseDto(optionalCategory.get());

        log.debug("CategoryService:getCategoryById EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), categoryFindResponseDto);


        return categoryFindResponseDto;

    }

    @Override
    public CategoryCreateResponseDto createCategory(CategoryCreateRequestDto categoryCreateRequestDto) throws CategoryNameAlreadyExistException {


        log.debug("CategoryService:createCategory EXECUTION STARTED. traceId: {} , brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), categoryCreateRequestDto);

        if (isCategoryExistByName(categoryCreateRequestDto.getName())) {
            log.error("CategoryService:createCategory EXECUTION ENDED. traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1501, categoryCreateRequestDto.getName()));
            throw new CategoryNameAlreadyExistException(ErrorCodes.E1501, categoryCreateRequestDto.getName());
        }
        Category category = categoryMapper.categoryCreateRequestDtoToCategory(categoryCreateRequestDto);
        Category savedCategory = categoryDao.save(category);
        CategoryCreateResponseDto categoryCreateResponseDto = categoryMapper.categoryToCategoryCreateResponseDto(savedCategory);

        log.debug("CategoryService:createCategory EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), categoryCreateRequestDto);
        return categoryCreateResponseDto;

    }

    @Override
    public CategoryUpdateResponseDto updateCategory(UUID id, CategoryUpdateRequestDto categoryUpdateRequestDto) throws CategoryNotFoundException {

        log.debug("CategoryService:updateCategory EXECUTION STARTED. traceId: {}, id: {}, brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), id, categoryUpdateRequestDto);

        Optional<Category> optionalCategory = categoryDao.findById(id);
        if (!optionalCategory.isPresent()) {
            throw new CategoryNotFoundException(ErrorCodes.E1503, id.toString());
        }


        Category category = categoryMapper.categoryUpdateRequestDtoToCategory(categoryUpdateRequestDto);
        category.setId(optionalCategory.get().getId());
        Category updatedCategory = categoryDao.save(category);
        CategoryUpdateResponseDto categoryUpdateResponseDto = categoryMapper.categoryToCategoryUpdateResponseDto(updatedCategory);

        log.debug("CategoryService:updateCategory EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), categoryUpdateResponseDto);

        return categoryUpdateResponseDto;
    }

    @Override
    public CategoryDeleteResponseDto deleteCategoryById(UUID id) throws CategoryNotFoundException {

        log.debug("CategoryService:deleteCategoryById EXECUTION STARTED. traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);


        Optional<Category> optionalCategory = categoryDao.findById(id);
        if (optionalCategory.isPresent()) {

            CategoryDeleteResponseDto categoryDeleteResponseDto = categoryMapper.convertToCategoryDeleteResponseDto(optionalCategory.get());

            Category category = optionalCategory.get();

            for (SubCategory subCategory : subCategoryDao.findByCategory(category)) {
                subCategoryDao.delete(subCategory);
            }
            categoryDao.deleteById(category.getId());

            log.debug("CategoryService:deleteCategoryById EXECUTION ENDED. response : {}", categoryDeleteResponseDto);

            return categoryDeleteResponseDto;
        } else {
            log.error("CategoryService:deleteCategoryById EXECUTION ENDED. traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1504, id.toString()));
            throw new CategoryNotFoundException(ErrorCodes.E1504, id.toString());
        }
    }

    @Override
    public Boolean isCategoryExistByName(String name) {

        log.debug("CategoryService:isCategoryExistByName EXECUTION STARTED. traceId: {}", TraceIdHolder.getTraceId());

        Optional<Category> optionalParentCategory = categoryDao.findByName(name);
        if (optionalParentCategory.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isCategoryExistById(UUID id) {

        log.debug("CategoryService:isCategoryExistById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Category> optionalCategory = categoryDao.findById(id);
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
            return categoryDao.findAllByPage(keyword, pageable)
                    .stream()
                    .map(categoryMapper::categoryToCategoryFindResponseDto)
                    .toList();
        }
        return categoryDao.findAll()
                .stream()
                .map(categoryMapper::categoryToCategoryFindResponseDto)
                .toList();

    }

    @Override
    public String updateImageById(UUID id, String fileName) throws CategoryNotFoundException {

        log.debug("CategoryService:updateImageById EXECUTION STARTED. traceId: {}, brandId:{}, fileName: {}", TraceIdHolder.getTraceId(), id, fileName);

        Optional<Category> optionalCategory = categoryDao.findById(id);
        if (optionalCategory.isEmpty()) {
            log.error("CategoryService:updateImageById EXECUTION ENDED. traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1505, id.toString()));
            throw new CategoryNotFoundException(ErrorCodes.E1505, id.toString());
        }
        Category category = optionalCategory.get();
        category.setImageName(fileName);
        Category updatedCategory = categoryDao.save(category);

        log.debug("CategoryService:updateImageById EXECUTION ENDED. traceId: {}, updatedImageName: {}", TraceIdHolder.getTraceId(), updatedCategory.getImageName());

        return updatedCategory.getImageName();


    }

    @Override
    public String getCategoryImageNameById(UUID id) {

        log.debug("CategoryService:getCategoryImageNameById EXECUTION STARTED. traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Category> optionalCategory = categoryDao.findById(id);

        if (!optionalCategory.isPresent()) {
            log.error("BrandService:getCategoryImageNameById EXECUTION ENDED. traceId: {}, errorMessage: Brand Not found", TraceIdHolder.getTraceId());
            throw new CategoryNotFoundException(ErrorCodes.E1506, id.toString());
        }

        String imageName = optionalCategory.get().getImageName();

        log.debug("CategoryService:getCategoryImageNameById EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), imageName);
        return imageName;
    }


}
