package com.pm.spring.ema.category.service.impl;


import com.pm.spring.ema.category.common.dto.subcategoryDto.request.SubCategoryCreateRequestDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.request.SubCategoryUpdateRequestDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryCreateResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryFindResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryUpdateResponseDto;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import com.pm.spring.ema.category.model.dao.categoryDao.CategoryDao;
import com.pm.spring.ema.category.model.dao.subCategoryDao.SubCategoryDao;
import com.pm.spring.ema.category.model.entity.Category;
import com.pm.spring.ema.category.model.entity.SubCategory;
import com.pm.spring.ema.category.exception.CategoryException.CategoryNameAlreadyExistException;
import com.pm.spring.ema.category.exception.SubCategoryException.SubCategoryNameAlreadyExistException;
import com.pm.spring.ema.category.exception.SubCategoryException.SubCategoryNotFoundException;
import com.pm.spring.ema.category.exception.CategoryException.CategoryNotFoundException;
import com.pm.spring.ema.category.mapper.SubCategoryMapper;
import com.pm.spring.ema.category.service.SubCategoryService;
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
@Service
@RequiredArgsConstructor
public class SubCategoryServiceImpl implements SubCategoryService {
    private static final int SUB_CATEGORY_PER_PAGE = 5;
    private final SubCategoryDao subCategoryDao;
    private final SubCategoryMapper subCategoryMapper;
    private final CategoryDao categoryDao;

    public List<SubCategoryFindResponseDto> getAllSubCategory() {

        log.info("SubCategoryService:getAllSubCategory execution started.");
        log.debug("SubCategoryService:getAllSubCategory traceId: {}", TraceIdHolder.getTraceId());

         List<SubCategoryFindResponseDto> subCategoryFindResponseDtoList = subCategoryDao.findAll()
                 .stream()
                .map(subCategoryMapper::subCategoryToSubCategoryFindResponseDto)
                .toList();
        log.debug("SubCategoryService:getAllSubCategory traceId: {}, response {} ", TraceIdHolder.getTraceId(), subCategoryFindResponseDtoList);
        log.info("SubCategoryService:getAllSubCategory execution ended.");

        return subCategoryFindResponseDtoList;
    }

    @Override
    public SubCategoryFindResponseDto getSubCategoryById(UUID id) throws SubCategoryNotFoundException {
        log.info("SubCategoryService:getSubCategoryById execution started.");
        log.debug("SubCategoryService:getSubCategoryById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<SubCategory> optionalSubCategory = subCategoryDao.findById(id);
        if (optionalSubCategory.isEmpty()) {
            log.error("SubCategoryService:getSubCategoryById traceId: {}, errorMessage: Brand Not found", TraceIdHolder.getTraceId());
            log.info("SubCategoryService:getSubCategoryById execution ended.");
        throw new SubCategoryNotFoundException(ErrorCodes.E1509, id.toString());
        }

        SubCategoryFindResponseDto subCategoryFindResponseDto = subCategoryMapper.subCategoryToSubCategoryFindResponseDto(optionalSubCategory.get());
        log.debug("SubCategoryService:getSubCategoryById traceId: {}, response: {}", TraceIdHolder.getTraceId(), subCategoryFindResponseDto);
        log.info("SubCategoryService:getSubCategoryById execution ended.");

        return subCategoryFindResponseDto;

    }

    @Override
    public SubCategoryCreateResponseDto createSubCategory(SubCategoryCreateRequestDto subCategoryCreateRequestDto) throws SubCategoryNameAlreadyExistException {
        log.info("SubCategoryService:createSubCategory execution started.");
        log.debug("SubCategoryService:createSubCategory traceId: {} , brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), subCategoryCreateRequestDto);

        if (isSubCategoryExistByName(subCategoryCreateRequestDto.getName())) {
            log.error("SubCategoryService:createSubCategory traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1508, subCategoryCreateRequestDto.getName()));
            throw new CategoryNameAlreadyExistException(ErrorCodes.E1508, subCategoryCreateRequestDto.getName());
        }


        SubCategory subCategory = subCategoryMapper.subCategoryCreateRequestDtoToSubCategory(subCategoryCreateRequestDto);
        UUID categoryId = subCategoryCreateRequestDto.getCategoryUUID();
        Category category = categoryDao.findById(categoryId).orElseThrow(() -> new CategoryNotFoundException(ErrorCodes.E1507,categoryId.toString()));
        subCategory.setCategory(category);
        SubCategory savedSubCategory = subCategoryDao.save(subCategory);
        SubCategoryCreateResponseDto subCategoryCreateResponseDto = subCategoryMapper.subCategoryToSubCategoryCreateResponseDto(savedSubCategory);
        subCategoryCreateResponseDto.setCategoryUUID(categoryId);

        log.debug("SubCategoryService:createSubCategory traceId: {}, response: {}", TraceIdHolder.getTraceId(), subCategoryCreateRequestDto);
        log.info("SubCategoryService:createSubCategory execution ended.");
        return subCategoryCreateResponseDto;
    }
    @Override
    public SubCategoryUpdateResponseDto updateSubCategory(final UUID id, SubCategoryUpdateRequestDto subCategoryUpdateRequestDto) throws SubCategoryNotFoundException {
        log.info("SubCategoryService:updateSubCategory execution started.");
        log.debug("SubCategoryService:updateSubCategory traceId: {}, id: {}, brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), id, subCategoryUpdateRequestDto);

        Optional<SubCategory> optionalSubCategory = subCategoryDao.findById(id);
        if (optionalSubCategory.isEmpty()) {
            throw new SubCategoryNotFoundException(ErrorCodes.E1510,id.toString());
        }


        SubCategory subCategory = subCategoryMapper.subCategoryUpdateRequestDtoToSubCategory(subCategoryUpdateRequestDto);
        subCategory.setId(id);
        subCategory.setCategory(optionalSubCategory.get().getCategory());
        SubCategory updatedSubCategory = subCategoryDao.save(subCategory);
        SubCategoryUpdateResponseDto subCategoryUpdateResponseDto = subCategoryMapper.subCategoryToSubCategoryUpdateResponseDto(updatedSubCategory);


        log.debug("SubCategoryService:updateSubCategory traceId: {}, response: {}", TraceIdHolder.getTraceId(), subCategoryUpdateResponseDto);
        log.info("SubCategoryService:updateSubCategory execution ended.");
        return subCategoryUpdateResponseDto;
    }

    @Override
    public void deleteSubCategoryById(UUID id) throws SubCategoryNotFoundException {

        log.info("SubCategoryService:deleteSubCategoryById execution started.");
        log.debug("SubCategoryService:deleteSubCategoryById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<SubCategory> optionalSubCategory = subCategoryDao.findById(id);
        if (optionalSubCategory.isEmpty()) {
            log.error("SubCategoryService:deleteSubCategoryById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1511, id.toString()));

            throw new SubCategoryNotFoundException(ErrorCodes.E1511, id.toString());
        }


        subCategoryDao.deleteById(id);
        log.info("SubCategoryService:deleteSubCategoryById execution ended.");

    }


    @Override
    public Boolean isSubCategoryExistByName(String name) {
        Optional<SubCategory> optionalCategory = subCategoryDao.findByName(name);
        if (optionalCategory.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public List<SubCategoryFindResponseDto> getSubCategoriesByCategoryId(UUID categoryId) {

        return subCategoryDao.findByCategory(Category.builder().id(categoryId).build())
                .stream()
                .map(subCategoryMapper::subCategoryToSubCategoryFindResponseDto)
                .toList();
    }


    @Override
    public List<SubCategoryFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, SUB_CATEGORY_PER_PAGE, sort);

        if (keyword != null) {
            return subCategoryDao.findAllByPage(keyword, (pageable))
                    .stream()
                    .map(subCategoryMapper::subCategoryToSubCategoryFindResponseDto)
                    .toList();
        }
        return subCategoryDao.findAll()
                .stream()
                .map(subCategoryMapper::subCategoryToSubCategoryFindResponseDto)
                .toList();

    }

    @Override
    public boolean isSubCategoryExistById(UUID id) {
        Optional<SubCategory> optionalSubCategory = subCategoryDao.findById(id);

        if (optionalSubCategory.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public String updateSubCategoryImageById(UUID id, String fileName) throws SubCategoryNotFoundException {
        log.info("SubCategoryService:updateSubCategoryImageById execution started.");
        log.debug("SubCategoryService:updateSubCategoryImageById traceId: {}, brandId:{}, fileName: {}", TraceIdHolder.getTraceId(), id, fileName);

        Optional<SubCategory> optionalSubCategory = subCategoryDao.findById(id);

        if (optionalSubCategory.isEmpty()) {
            log.error("SubCategoryService:updateSubCategoryImageById traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1505, id.toString()));
            throw new SubCategoryNotFoundException(ErrorCodes.E1512, id.toString());
        }

        SubCategory subCategory = optionalSubCategory.get();
        subCategory.setImageName(fileName);
        SubCategory updatedSubCategory = subCategoryDao.save(subCategory);

        log.debug("SubCategoryService:updateSubCategoryImageById traceId: {}, updatedImageName: {}", TraceIdHolder.getTraceId(), updatedSubCategory.getImageName());
        log.info("SubCategoryService:updateSubCategoryImageById execution ended.");
        return updatedSubCategory.getImageName();


    }


}
