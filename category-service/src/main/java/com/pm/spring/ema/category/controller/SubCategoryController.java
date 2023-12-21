package com.pm.spring.ema.category.controller;

import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryDeleteResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryFindResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryUpdateResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.request.SubCategoryCreateRequestDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.request.SubCategoryUpdateRequestDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryCreateResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryDeleteResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryFindResponseDto;
import com.pm.spring.ema.category.common.dto.subcategoryDto.response.SubCategoryUpdateResponseDto;
import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.StatusType;
import com.pm.spring.ema.common.util.api.SuccessResponse;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.category.exception.SubCategoryException.SubCategoryNameAlreadyExistException;
import com.pm.spring.ema.category.exception.SubCategoryException.SubCategoryNotFoundException;
import com.pm.spring.ema.category.service.SubCategoryService;
import com.pm.spring.ema.category.utils.TraceIdHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@Tag(name = "sub-category")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;


    @Operation(summary = "Create a new sub-category", description = "Add a new sub-category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Sub-category created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SubCategoryCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Sub-category already exist", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("create")
    public ResponseEntity<SuccessResponse> createSubCategory(@RequestBody @Valid final SubCategoryCreateRequestDto subCategoryCreateRequestDto) throws SubCategoryNameAlreadyExistException {
        log.info("SubCategoryController:createSubCategory execution started.");
        log.debug("SubCategoryController:createSubCategory traceId: {} request payload: {}", TraceIdHolder.getTraceId(), subCategoryCreateRequestDto);


        SubCategoryCreateResponseDto subCategoryCreateResponseDto = subCategoryService.createSubCategory(subCategoryCreateRequestDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("SubCategoryController:createSubCategory traceId: {} response: {}", TraceIdHolder.getTraceId(), subCategoryCreateResponseDto);
        log.info("SubCategoryController:createSubCategory execution ended.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(SuccessResponse.builder()
                        .data(subCategoryCreateResponseDto)
                        .status(StatusType.SUCCESS)
                        .build());

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Sub-category found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SubCategoryFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Sub-category could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get a sub-category by id", description = "Get a sub-category by id")
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getSubCategoryById(@PathVariable final UUID id) {
        log.info("SubCategoryController:getSubCategoryById execution started.");
        log.info("SubCategoryController:getSubCategoryById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        SubCategoryFindResponseDto subCategoryFindResponseDto = subCategoryService.getSubCategoryById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("SubCategoryController:getSubCategoryById traceId: {} response : {}", TraceIdHolder.getTraceId(), subCategoryFindResponseDto);
        log.info("SubCategoryController:getSubCategoryById execution ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder()
                        .data(subCategoryFindResponseDto)
                        .status(StatusType.SUCCESS)
                        .build());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Sub-categories found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SubCategoryFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Sub-categories could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get all sub-categories", description = "Fetch all the sub-categories")
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


    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Sub-categories updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SubCategoryUpdateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Sub-categories could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Update a Sub-category by id", description = "Update a Sub-category")
    @PutMapping("update/{id}")
    public ResponseEntity<SuccessResponse> updateSubCategory(@PathVariable UUID id, @RequestBody SubCategoryUpdateRequestDto subCategoryUpdateRequestDto) {
        log.info("SubCategoryController:updateSubCategory started.");
        log.info("SubCategoryController:updateSubCategory traceId: {} request id: {} payload: {}", TraceIdHolder.getTraceId(), id, subCategoryUpdateRequestDto);

        SubCategoryUpdateResponseDto subCategoryUpdateResponseDto = subCategoryService.updateSubCategory(id, subCategoryUpdateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("SubCategoryController:updateSubCategory traceId: {} response: {}", TraceIdHolder.getTraceId(), subCategoryUpdateResponseDto);
        log.info("SubCategoryController:updateSubCategory ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder()
                        .data(subCategoryUpdateResponseDto)
                        .status(StatusType.SUCCESS)
                        .build());

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Sub-category deleted.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SubCategoryDeleteResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Sub-category could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Delete a Sub-category by id", description = "Delete a Sub-category")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<SuccessResponse> deleteSubCategoryById(@PathVariable final UUID id) {
        log.info("SubCategoryController:deleteSubCategoryById started.");
        log.info("SubCategoryController:deleteSubCategoryById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        SubCategoryDeleteResponseDto categoryDeleteResponseDto = subCategoryService.deleteSubCategoryById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("SubCategoryController:deleteSubCategoryById traceId: {}", TraceIdHolder.getTraceId());
        log.info("SubCategoryController:deleteSubCategoryById ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder().data(categoryDeleteResponseDto)
                        .status(StatusType.SUCCESS).build());


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
            throw new SubCategoryNotFoundException(ErrorCodes.E1513, id.toString());
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

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Sub-categories found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = SubCategoryFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Sub-categories could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get all sub-categories associated with one categoryId ",description = "Fetch all the sub-categories associated with one categoryId")
    @GetMapping(value = "{categoryId}", produces = "application/json")
    public ResponseEntity<List<SubCategoryFindResponseDto>> getSubCategoriesByCategoryId(@PathVariable final UUID categoryId) {

        log.info("SubCategoryController:getSubCategoriesByCategoryId execution started.");
        log.info("SubCategoryController:getSubCategoriesByCategoryId traceId: {} request id: {}", TraceIdHolder.getTraceId(), categoryId);

        List<SubCategoryFindResponseDto> subCategoryFindResponseDtoList = subCategoryService.getSubCategoriesByCategoryId(categoryId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());


        log.info("SubCategoryController:getSubCategoriesByCategoryId traceId: {} response : {}", TraceIdHolder.getTraceId(), subCategoryFindResponseDtoList);
        log.info("SubCategoryController:getSubCategoriesByCategoryId execution ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(subCategoryFindResponseDtoList);


    }

}
