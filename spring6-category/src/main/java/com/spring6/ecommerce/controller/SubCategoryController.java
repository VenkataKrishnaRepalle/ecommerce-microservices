package com.spring6.ecommerce.controller;


import com.spring6.common.dto.SubCategoryFindResponseDto;
import com.spring6.common.utils.FileUploadUtils;
import com.spring6.common.utils.GlobalConstants;
import com.spring6.ecommerce.dto.SubCategoryCreateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryCreateResponseDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateResponseDto;
import com.spring6.ecommerce.exception.SubCategoryNameAlreadyExistException;
import com.spring6.ecommerce.exception.SubCategoryNotFoundException;
import com.spring6.ecommerce.service.SubCategoryService;
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
@RequestMapping("api/sub-category")
@Tag(name = "SubCategory")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @Operation(summary = "Create new sub-category")
    @PostMapping("create")
    public ResponseEntity<SubCategoryCreateResponseDto> createSubCategory(@RequestBody @Valid final SubCategoryCreateRequestDto subCategoryCreateRequestDto) throws SubCategoryNameAlreadyExistException {
        log.info("SubCategoryController:createSubCategory execution started.");
        log.debug("SubCategoryController:createSubCategory traceId: {} request payload: {}", TraceIdHolder.getTraceId(), subCategoryCreateRequestDto);


        SubCategoryCreateResponseDto subCategoryCreateResponseDto = subCategoryService.createSubCategory(subCategoryCreateRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("SubCategoryController:createSubCategory traceId: {} response: {}", TraceIdHolder.getTraceId(), subCategoryCreateResponseDto);
        log.info("SubCategoryController:createSubCategory execution ended.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(subCategoryCreateResponseDto);
    }

    @Operation(summary = "Get sub-category by id")
    @GetMapping("{id}")
    public ResponseEntity<SubCategoryFindResponseDto> getSubCategoryById(@PathVariable final UUID id) {
        log.info("SubCategoryController:getSubCategoryById execution started.");
        log.info("SubCategoryController:getSubCategoryById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        SubCategoryFindResponseDto subCategoryFindResponseDto = subCategoryService.getSubCategoryById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("SubCategoryController:getSubCategoryById traceId: {} response : {}", TraceIdHolder.getTraceId(), subCategoryFindResponseDto);
        log.info("SubCategoryController:getSubCategoryById execution ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(subCategoryFindResponseDto);

    }

    @Operation(summary = "Get all sub-category")
    @GetMapping("list")
    public ResponseEntity<List<SubCategoryFindResponseDto>> getAllSubCategory() {
        log.info("SubCategoryController:getAllSubCategory started.");
        log.info("SubCategoryController:getAllSubCategory traceId: {}", TraceIdHolder.getTraceId());

        List<SubCategoryFindResponseDto> subCategoryFindResponseDtoList = subCategoryService.getAllSubCategory();

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());


        log.info("SubCategoryController:getAllSubCategory traceId: {} response : {}", TraceIdHolder.getTraceId(), subCategoryFindResponseDtoList);
        log.info("SubCategoryController:getAllSubCategory execution ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(subCategoryFindResponseDtoList);

    }


    @Operation(summary = "Update sub-category by id")
    @PutMapping("update/{id}")
    public ResponseEntity<SubCategoryUpdateResponseDto>updateSubCategory(@PathVariable UUID id, @RequestBody SubCategoryUpdateRequestDto subCategoryUpdateRequestDto ) {
        log.info("SubCategoryController:updateSubCategory started.");
        log.info("SubCategoryController:updateSubCategory traceId: {} request id: {} payload: {}", TraceIdHolder.getTraceId(), id, subCategoryUpdateRequestDto);

        SubCategoryUpdateResponseDto subCategoryUpdateResponseDto = subCategoryService.updateSubCategory(id, subCategoryUpdateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("SubCategoryController:updateSubCategory traceId: {} response: {}", TraceIdHolder.getTraceId(), subCategoryUpdateResponseDto);
        log.info("SubCategoryController:updateSubCategory ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(subCategoryUpdateResponseDto);


    }

    @Operation(summary = "delete sub-category by id")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteSubCategoryById(@PathVariable final UUID id) {
        log.info("SubCategoryController:deleteSubCategoryById started.");
        log.info("SubCategoryController:deleteSubCategoryById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        subCategoryService.deleteSubCategoryById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("SubCategoryController:deleteSubCategoryById traceId: {}", TraceIdHolder.getTraceId());
        log.info("SubCategoryController:deleteSubCategoryById ended.");
        return ResponseEntity.noContent()
                .headers(headers)
                .build();

    }

    @Operation(summary = "Sort and Filter for sub-category list page")
    @GetMapping("findByPage/{pageNumber}")
    public List<SubCategoryFindResponseDto> findByPage(@PathVariable("pageNumber") int pageNumber,
                                                       @RequestParam("sortField") String sortField,
                                                       @RequestParam("sortDir") String sortDir,
                                                       @RequestParam("keyword") String keyword) {

        return subCategoryService.findByPage(pageNumber, sortField, sortDir, keyword);

    }

    @Operation(summary = "Upload Image of sub-category ")
    @PostMapping(value = "upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> uploadSubCategoryImage(@RequestParam @NotNull final UUID id,
                                                  @NotNull @RequestParam(name = "fileImage", value = "fileImage") final MultipartFile multipartFile)
            throws IOException, SubCategoryNotFoundException {

        log.info("SubCategoryController:uploadSubCategoryImage started.");
        log.info("SubCategoryController:uploadSubCategoryImage traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        if (!subCategoryService.isSubCategoryExistById(id)) {
            throw new SubCategoryNotFoundException("Sub-Category does not exist  with id " + id.toString());
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uploadDir = "spring6-ecommerce-category/sub-category-images";
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());

            subCategoryService.updateSubCategoryImageById(id, fileName);
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("SubCategoryController:uploadSubCategoryImage traceId: {}", TraceIdHolder.getTraceId());
        log.info("SubCategoryController:uploadSubCategoryImage ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @Operation(summary = "Get all sub-categories associated with One categoryId ")
    @GetMapping(value = "{categoryId}", produces = "application/json")
    public ResponseEntity<List<SubCategoryFindResponseDto>> getSubCategoriesByCategoryId(@PathVariable final UUID categoryId) {

        log.info("SubCategoryController:getSubCategoriesByCategoryId execution started.");
        log.info("SubCategoryController:getSubCategoriesByCategoryId traceId: {} request id: {}", TraceIdHolder.getTraceId(), categoryId);

        List<SubCategoryFindResponseDto> subCategoryFindResponseDtoList = subCategoryService.findByCategoryId(categoryId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());


        log.info("SubCategoryController:getSubCategoriesByCategoryId traceId: {} response : {}", TraceIdHolder.getTraceId(), subCategoryFindResponseDtoList);
        log.info("SubCategoryController:getSubCategoriesByCategoryId execution ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(subCategoryFindResponseDtoList);


    }

}
