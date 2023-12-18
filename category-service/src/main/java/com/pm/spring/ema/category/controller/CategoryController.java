package com.pm.spring.ema.category.controller;

import com.pm.spring.ema.category.common.dto.categoryDto.request.CategoryCreateRequestDto;
import com.pm.spring.ema.category.common.dto.categoryDto.request.CategoryUpdateRequestDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryCreateResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryFindResponseDto;
import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryUpdateResponseDto;
import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.StatusType;
import com.pm.spring.ema.common.util.api.SuccessResponse;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.category.exception.CategoryException.CategoryNameAlreadyExistException;
import com.pm.spring.ema.category.exception.CategoryException.CategoryNotFoundException;
import com.pm.spring.ema.category.service.CategoryService;
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
@RequestMapping("category")
@Tag(name = "Category")
public class CategoryController {

    private final CategoryService categoryService;

    @Operation(summary = "Create a new category", description = "Add a new category")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Category created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryCreateRequestDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Category already exist", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("create")
    public ResponseEntity<SuccessResponse> createCategory(@RequestBody @Valid final CategoryCreateRequestDto categoryCreateRequestDto) throws CategoryNameAlreadyExistException {

        log.info("CategoryController:createCategory execution started.");
        log.debug("CategoryController:createCategory traceId: {} request payload: {}", TraceIdHolder.getTraceId(), categoryCreateRequestDto);

        CategoryCreateResponseDto categoryCreateResponseDto = categoryService.createCategory(categoryCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("CategoryController:createCategory traceId: {} response: {}", TraceIdHolder.getTraceId(), categoryCreateResponseDto);
        log.info("CategoryController:createCategory execution ended.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(SuccessResponse.builder()
                        .data(categoryCreateResponseDto)
                        .status(StatusType.SUCCESS)
                        .build());
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Category found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Category could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get a category by categoryId")
    @GetMapping("{categoryId}")
    public ResponseEntity<SuccessResponse> getCategoryById(@PathVariable final UUID categoryId) {

        log.info("CategoryController:getCategoryById execution started.");

        log.info("CategoryController:getCategoryById traceId: {} request id: {}", TraceIdHolder.getTraceId(), categoryId);

        CategoryFindResponseDto categoryFindResponseDto = categoryService.getCategoryById(categoryId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("CategoryController:getCategoryById traceId: {} response : {}", TraceIdHolder.getTraceId(), categoryFindResponseDto);

        log.info("CategoryController:getCategoryById execution ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder().data(categoryFindResponseDto).status(StatusType.SUCCESS).build());

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Categories found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Categories could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get all categories", description = "Fetch all the categories")
    @GetMapping("list")
    public ResponseEntity<List<CategoryFindResponseDto>> getAllCategory() {

        log.info("CategoryController:getAllCategory started.");

        log.info("CategoryController:getAllCategory traceId: {}", TraceIdHolder.getTraceId());

        List<CategoryFindResponseDto> categoryFindResponseDtoList = categoryService.getAllCategory();

        HttpHeaders headers = new HttpHeaders();

        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("CategoryController:getAllCategory traceId: {} response : {}", TraceIdHolder.getTraceId(), categoryFindResponseDtoList);

        log.info("CategoryController:getAllCategory execution ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(categoryFindResponseDtoList);
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Category updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CategoryFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Category could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @Operation(summary = "Update a category by id",description = "Update a category by categoryId")
    @PutMapping("update/{categoryId}")
    public ResponseEntity<CategoryUpdateResponseDto> updateCategory(@PathVariable UUID categoryId, @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {

        log.info("CategoryController:updateCategory started.");

        log.info("CategoryController:updateCategory traceId: {} request id: {} payload: {}", TraceIdHolder.getTraceId(), categoryId, categoryUpdateRequestDto);

        CategoryUpdateResponseDto categoryUpdateResponseDto = categoryService.updateCategory(categoryId, categoryUpdateRequestDto);


        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("CategoryController:updateCategory traceId: {} response: {}", TraceIdHolder.getTraceId(), categoryUpdateResponseDto);
        log.info("CategoryController:updateCategory ended.");
        return ResponseEntity.ok()
                .headers(headers)
                .body(categoryUpdateResponseDto);

    }

    @ApiResponses(value = {
//            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Category deleted.", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ContactFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Category could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Delete a category by id",description = "Delete a category")
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

        log.info("CategoryController:uploadCategoryImage traceId: {}", TraceIdHolder.getTraceId());
        log.info("CategoryController:uploadCategoryImage ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }


}
