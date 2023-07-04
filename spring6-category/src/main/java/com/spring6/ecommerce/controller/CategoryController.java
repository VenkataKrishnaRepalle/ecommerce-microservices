package com.spring6.ecommerce.controller;

import com.spring6.common.dto.CategoryFindResponseDto;
import com.spring6.common.exeption.ErrorCodes;
import com.spring6.common.utils.FileUploadUtils;
import com.spring6.common.utils.GlobalConstants;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
import com.spring6.ecommerce.exception.CategoryNameAlreadyExistException;
import com.spring6.ecommerce.exception.CategoryNotFoundException;
import com.spring6.ecommerce.service.CategoryService;
import com.spring6.ecommerce.utils.TraceIdHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
@Tag(name = "Category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Create new Category")
    @PostMapping("create")
    public ResponseEntity<CategoryCreateResponseDto> createCategory(@RequestBody @Valid final CategoryCreateRequestDto categoryCreateRequestDto) throws CategoryNameAlreadyExistException {

        log.info("CategoryController:createCategory execution started.");
        log.debug("CategoryController:createCategory traceId: {} request payload: {}", TraceIdHolder.getTraceId(), categoryCreateRequestDto);

        CategoryCreateResponseDto savedCategory = categoryService.createCategory(categoryCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("CategoryController:createCategory traceId: {} response: {}", TraceIdHolder.getTraceId(), savedCategory);
        log.info("CategoryController:createCategory execution ended.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(savedCategory);
    }

    @Operation(summary = "Get category by id")
    @GetMapping("{id}")
    public ResponseEntity<CategoryFindResponseDto> getCategoryById(@PathVariable final UUID id) {
        log.info("CategoryController:getCategoryById execution started.");
        log.info("CategoryController:getCategoryById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);
        CategoryFindResponseDto categoryFindResponseDto = categoryService.getCategoryById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("CategoryController:getCategoryById traceId: {} response : {}", TraceIdHolder.getTraceId(), categoryFindResponseDto);
        log.info("CategoryController:getCategoryById execution ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(categoryFindResponseDto);

    }

    @Operation(summary = "Get all categories")
    @GetMapping("list")
    public ResponseEntity<List<CategoryFindResponseDto>> getAllCategory() {
        log.info("CategoryController:getAllCategory started.");
        log.info("CategoryController:getAllCategory traceId: {}", TraceIdHolder.getTraceId());
        List<CategoryFindResponseDto> categoryFindResponseDtoList = categoryService.getAllCategory();
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("CategoryController:getCategoryById traceId: {} response : {}", TraceIdHolder.getTraceId(), categoryFindResponseDtoList);
        log.info("CategoryController:getCategoryById execution ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(categoryFindResponseDtoList);
    }

    @Operation(summary = "Update category by id")
    @PutMapping("update/{id}")
    public ResponseEntity<CategoryUpdateResponseDto> updateCategory(@PathVariable UUID id, @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        log.info("CategoryController:updateCategory started.");
        log.info("CategoryController:updateCategory traceId: {} request id: {} payload: {}", TraceIdHolder.getTraceId(), id, categoryUpdateRequestDto);

        CategoryUpdateResponseDto categoryUpdateResponseDto = categoryService.updateCategory(id, categoryUpdateRequestDto);


        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("CategoryController:updateCategory traceId: {} response: {}", TraceIdHolder.getTraceId(), categoryUpdateResponseDto);
        log.info("CategoryController:updateCategory ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(categoryUpdateResponseDto);

    }

    @Operation(summary = "delete category by id")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteCategoryById(@PathVariable final UUID id) {
        log.info("CategoryController:deleteCategoryById started.");
        log.info("CategoryController:deleteCategoryById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        categoryService.deleteCategoryById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("CategoryController:deleteCategoryById traceId: {}", TraceIdHolder.getTraceId());
        log.info("CategoryController:deleteCategoryById ended.");
        return ResponseEntity.noContent()
                .headers(headers)
                .build();
    }

    @Operation(summary = "Sort and Filter for Category list page")
    @GetMapping("findByPage/{pageNumber}")
    public List<CategoryFindResponseDto> findByPage(@PathVariable("pageNumber") int pageNumber,
                                                    @RequestParam("sortField") String sortField,
                                                    @RequestParam("sortDir") String sortDir,
                                                    @RequestParam("keyword") String keyword) {

        return categoryService.findByPage(pageNumber, sortField, sortDir, keyword);

    }

    @Operation(summary = "Upload Image of Category ")
    @PostMapping(value = "upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadCategoryImage(@RequestParam @NotNull final UUID id,
                                                    @NotNull @RequestParam(name = "fileImage", value = "fileImage") final MultipartFile multipartFile)
            throws IOException, CategoryNotFoundException {
        log.info("CategoryController:uploadCategoryImage started.");
        log.info("CategoryController:uploadCategoryImage traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        if (!categoryService.isCategoryExistById(id)) {
            throw new CategoryNotFoundException(ErrorCodes.E1506, id.toString());
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uploadDir = "spring6-category/category-images";
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());

            categoryService.updateImageById(id, fileName);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("BrandController:uploadBrandImage traceId: {}", TraceIdHolder.getTraceId());
        log.info("BrandController:uploadBrandImage ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }


}
