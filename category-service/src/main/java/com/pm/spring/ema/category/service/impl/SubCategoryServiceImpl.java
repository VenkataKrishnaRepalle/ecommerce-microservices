package com.pm.spring.ema.category.service.impl;

import com.pm.spring.ema.category.repository.CategoryRepository;
import com.pm.spring.ema.category.repository.SubCategoryRepository;
import com.pm.spring.ema.category.model.Category;
import com.pm.spring.ema.category.model.SubCategory;

import com.pm.spring.ema.category.mapper.SubCategoryMapper;
import com.pm.spring.ema.category.service.SubCategoryService;
import com.pm.spring.ema.category.utils.TraceIdHolder;
import com.pm.spring.ema.common.util.dto.SubCategoryDto;
import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import com.pm.spring.ema.common.util.exception.utils.ErrorMessage;
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
public class SubCategoryServiceImpl implements SubCategoryService {
    private static final int SUB_CATEGORY_PER_PAGE = 5;
    private final SubCategoryRepository subCategoryRepository;
    private final SubCategoryMapper subCategoryMapper;
    private final CategoryRepository categoryRepository;

    public List<SubCategoryDto> getAllSubCategory() {


        log.debug("SubCategoryService:getAllSubCategory EXECUTION STARTED.  traceId: {}", TraceIdHolder.getTraceId());

        List<SubCategoryDto> subCategoryFindResponseDtoList = subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::toSubCategoryDto)
                .toList();
        log.debug("SubCategoryService:getAllSubCategory EXECUTION ENDED. traceId: {}, response {} ", TraceIdHolder.getTraceId(), subCategoryFindResponseDtoList);

        return subCategoryFindResponseDtoList;
    }

    @Override
    public SubCategoryDto getSubCategoryById(UUID id) throws NotFoundException {

        log.debug("SubCategoryService:getSubCategoryById EXECUTION STARTED.  traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(id);
        if (optionalSubCategory.isEmpty()) {
            log.error("SubCategoryService:getSubCategoryById EXECUTION ENDED. traceId: {} ", TraceIdHolder.getTraceId());
            throw new NotFoundException(ErrorCodes.E1509, id.toString());
        }

        SubCategoryDto subCategoryFindResponseDto = subCategoryMapper.toSubCategoryDto(optionalSubCategory.get());
        log.debug("SubCategoryService:getSubCategoryById EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), subCategoryFindResponseDto);

        return subCategoryFindResponseDto;

    }

    @Override
    public SubCategoryDto createSubCategory(SubCategoryDto subCategoryCreateRequestDto) throws FoundException {

        log.debug("SubCategoryService:createSubCategory EXECUTION STARTED. traceId: {} , brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), subCategoryCreateRequestDto);

        if (isSubCategoryExistByName(subCategoryCreateRequestDto.getName())) {
            log.error("SubCategoryService:createSubCategory EXECUTION ENDED. traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1508, subCategoryCreateRequestDto.getName()));
            throw new FoundException(ErrorCodes.E1508, subCategoryCreateRequestDto.getName());
        }


        SubCategory subCategory = subCategoryMapper.toSubCategory(subCategoryCreateRequestDto);
        UUID categoryId = subCategoryCreateRequestDto.getCategoryUUID();
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException(ErrorCodes.E1507, categoryId.toString()));
        subCategory.setCategory(category);
        SubCategory savedSubCategory = subCategoryRepository.save(subCategory);
        SubCategoryDto subCategoryCreateResponseDto = subCategoryMapper.toSubCategoryDto(savedSubCategory);
        subCategoryCreateResponseDto.setCategoryUUID(categoryId);

        log.debug("SubCategoryService:createSubCategory EXECUTION ENDED. traceId: {}, response: {}", TraceIdHolder.getTraceId(), subCategoryCreateRequestDto);
        return subCategoryCreateResponseDto;
    }

    @Override
    public SubCategoryDto updateSubCategory(final UUID id, SubCategoryDto subCategoryRequestDto) throws NotFoundException {
        log.debug("SubCategoryService:updateSubCategory EXECUTION STARTED. traceId: {}, id: {}, brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), id, subCategoryRequestDto);

        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(id);
        if (optionalSubCategory.isEmpty()) {
            throw new NotFoundException(ErrorCodes.E1510, id.toString());
        }


        SubCategory subCategory = subCategoryMapper.toSubCategory(subCategoryRequestDto);
        subCategory.setId(id);
        subCategory.setCategory(optionalSubCategory.get().getCategory());
        SubCategory updatedSubCategory = subCategoryRepository.save(subCategory);
        SubCategoryDto subCategoryUpdateResponseDto = subCategoryMapper.toSubCategoryDto(updatedSubCategory);


        log.debug("SubCategoryService:updateSubCategory EXECUTION ENDED.traceId: {}, response: {}", TraceIdHolder.getTraceId(), subCategoryUpdateResponseDto);
        return subCategoryUpdateResponseDto;
    }

    @Override
    public SubCategoryDto deleteSubCategoryById(UUID id) throws NotFoundException {

        log.debug("SubCategoryService:deleteSubCategoryById EXECUTION STARTED. traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(id);
        if (optionalSubCategory.isPresent()) {

            SubCategoryDto categoryDeleteResponseDto = subCategoryMapper.toSubCategoryDto(optionalSubCategory.get());

            subCategoryRepository.deleteById(id);

            log.debug("SubCategoryService:deleteSubCategoryById EXECUTION ENDED.");

            return categoryDeleteResponseDto;
        } else {
            log.error("SubCategoryService:deleteSubCategoryById EXECUTION ENDED. traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1511, id.toString()));

            throw new NotFoundException(ErrorCodes.E1511, id.toString());
        }


    }


    @Override
    public Boolean isSubCategoryExistByName(String name) {
        Optional<SubCategory> optionalCategory = subCategoryRepository.findByName(name);
        if (optionalCategory.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public List<SubCategoryDto> getSubCategoriesByCategoryId(UUID categoryId) {

        return subCategoryRepository.findByCategory(Category.builder().id(categoryId).build())
                .stream()
                .map(subCategoryMapper::toSubCategoryDto)
                .toList();
    }


    @Override
    public List<SubCategoryDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, SUB_CATEGORY_PER_PAGE, sort);

        if (keyword != null) {
            return subCategoryRepository.findAllByPage(keyword, (pageable))
                    .stream()
                    .map(subCategoryMapper::toSubCategoryDto)
                    .toList();
        }
        return subCategoryRepository.findAll()
                .stream()
                .map(subCategoryMapper::toSubCategoryDto)
                .toList();

    }

    @Override
    public boolean isSubCategoryExistById(UUID id) {
        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(id);

        if (optionalSubCategory.isPresent()) {
            return Boolean.TRUE;
        }

        return Boolean.FALSE;
    }

    @Override
    public String updateSubCategoryImageById(UUID id, String fileName) throws NotFoundException {

        log.debug("SubCategoryService:updateSubCategoryImageById EXECUTION STARTED. traceId: {}, brandId:{}, fileName: {}", TraceIdHolder.getTraceId(), id, fileName);

        Optional<SubCategory> optionalSubCategory = subCategoryRepository.findById(id);

        if (optionalSubCategory.isEmpty()) {
            log.error("SubCategoryService:updateSubCategoryImageById EXECUTION ENDED. traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E1505, id.toString()));
            throw new NotFoundException(ErrorCodes.E1512, id.toString());
        }

        SubCategory subCategory = optionalSubCategory.get();
        subCategory.setImageName(fileName);
        SubCategory updatedSubCategory = subCategoryRepository.save(subCategory);

        log.debug("SubCategoryService:updateSubCategoryImageById EXECUTION ENDED. traceId: {}, updatedImageName: {}", TraceIdHolder.getTraceId(), updatedSubCategory.getImageName());
        return updatedSubCategory.getImageName();


    }


}
