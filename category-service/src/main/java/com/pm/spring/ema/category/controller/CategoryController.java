package com.pm.spring.ema.category.controller;

import com.pm.spring.ema.category.service.CategoryService;
import com.pm.spring.ema.category.utils.TraceIdHolder;
import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.StatusType;
import com.pm.spring.ema.common.util.api.SuccessResponse;
import com.pm.spring.ema.common.util.dto.CategoryDto;
import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/category")
@Tag(name = "category")
public class CategoryController {

  private final CategoryService categoryService;

  @Operation(summary = "Create a new category", description = "Add a new category")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Category created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.CONFLICT,
            description = "Category already exist",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = ErrorResponse.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping("create")
  public ResponseEntity<SuccessResponse> createCategory(
      @RequestBody @Valid final CategoryDto categoryRequestDto) throws FoundException {

    log.debug(
        "CategoryController:createCategory EXECUTION STARTED. traceId: {} request payload: {}",
        TraceIdHolder.getTraceId(),
        categoryRequestDto);

    CategoryDto categoryResponseDto = categoryService.createCategory(categoryRequestDto);

    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "CategoryController:createCategory EXECUTION ENDED. traceId: {} response: {}",
        TraceIdHolder.getTraceId(),
        categoryResponseDto);

    return ResponseEntity.status(HttpStatus.CREATED)
        .headers(headers)
        .body(
            SuccessResponse.builder().data(categoryResponseDto).status(StatusType.SUCCESS).build());
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Category found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Category could not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @Operation(summary = "Get a category by id", description = "Get a category by id")
  @GetMapping("{categoryId}")
  public ResponseEntity<CategoryDto> getCategoryById(@PathVariable final UUID categoryId) {

    log.debug(
        "CategoryController:getCategoryById EXECUTION STARTED. traceId: {} request id: {}",
        TraceIdHolder.getTraceId(),
        categoryId);

    CategoryDto categoryFindResponseDto = categoryService.getCategoryById(categoryId);

    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "CategoryController:getCategoryById  EXECUTION ENDED. traceId: {} response : {}",
        TraceIdHolder.getTraceId(),
        categoryFindResponseDto);

    return ResponseEntity.ok().headers(headers).body(categoryFindResponseDto);
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Categories found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Categories could not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @Operation(summary = "Get all categories", description = "Fetch all the categories")
  @GetMapping("list")
  public ResponseEntity<List<CategoryDto>> getAllCategory() {

    log.debug(
        "CategoryController:getAllCategory EXECUTION STARTED. traceId: {}",
        TraceIdHolder.getTraceId());

    List<CategoryDto> categoryFindResponseDtoList = categoryService.getAllCategory();

    HttpHeaders headers = new HttpHeaders();

    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "CategoryController:getAllCategory EXECUTION ENDED. traceId: {} response : {}",
        TraceIdHolder.getTraceId(),
        categoryFindResponseDtoList);

    return ResponseEntity.ok().headers(headers).body(categoryFindResponseDtoList);
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Category updated",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Category could not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @Operation(summary = "Update a category by id", description = "Update a category by categoryId")
  @PutMapping("update/{categoryId}")
  public ResponseEntity<SuccessResponse> updateCategory(
      @PathVariable UUID categoryId, @RequestBody CategoryDto CategoryRequestDto) {

    log.debug(
        "CategoryController:updateCategory EXECUTION STARTED. traceId: {} request id: {} payload: {}",
        TraceIdHolder.getTraceId(),
        categoryId,
        CategoryRequestDto);

    CategoryDto categoryUpdateResponseDto =
        categoryService.updateCategory(categoryId, CategoryRequestDto);

    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "CategoryController:updateCategory EXECUTION ENDED. traceId: {} response: {}",
        TraceIdHolder.getTraceId(),
        categoryUpdateResponseDto);
    return ResponseEntity.ok()
        .headers(headers)
        .body(
            SuccessResponse.builder()
                .data(categoryUpdateResponseDto)
                .status(StatusType.SUCCESS)
                .build());
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Category deleted.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = CategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Category could not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @Operation(summary = "Delete a category by id", description = "Delete a category")
  @DeleteMapping("delete/{id}")
  public ResponseEntity<SuccessResponse> deleteCategoryById(@PathVariable final UUID id) {

    log.debug(
        "CategoryController:deleteCategoryById EXECUTION STARTED. traceId: {} request id: {}",
        TraceIdHolder.getTraceId(),
        id);

    CategoryDto categoryDeleteResponseDto = categoryService.deleteCategoryById(id);

    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "CategoryController:deleteCategoryById EXECUTION ENDED. traceId: {}",
        TraceIdHolder.getTraceId());
    return ResponseEntity.ok()
        .headers(headers)
        .body(
            SuccessResponse.builder()
                .data(categoryDeleteResponseDto)
                .status(StatusType.SUCCESS)
                .build());
  }

  @Operation(summary = "Sort and Filter for Category list page")
  @GetMapping("findByPage/{pageNumber}")
  public List<CategoryDto> findByPage(
      @PathVariable("pageNumber") int pageNumber,
      @RequestParam("sortField") String sortField,
      @RequestParam("sortDir") String sortDir,
      @RequestParam("keyword") String keyword) {

    return categoryService.findByPage(pageNumber, sortField, sortDir, keyword);
  }

  @Operation(summary = "Upload Image of Category ")
  @PostMapping(value = "upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<Void> uploadCategoryImage(
      @RequestParam @NotNull final UUID id,
      @NotNull @RequestParam(name = "fileImage", value = "fileImage")
          final MultipartFile multipartFile)
      throws IOException, NotFoundException {
    log.debug(
        "CategoryController:uploadCategoryImage EXECUTION STARTED. traceId: {} request id: {}",
        TraceIdHolder.getTraceId(),
        id);

    if (!categoryService.isCategoryExistById(id)) {
      throw new NotFoundException(ErrorCodes.E1506, id.toString());
    }

    if (!multipartFile.isEmpty()) {
      String fileName =
          StringUtils.cleanPath(Objects.requireNonNull(multipartFile.getOriginalFilename()));

      String uploadDir = "spring6-category/category-images";
      FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());

      categoryService.updateImageById(id, fileName);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "CategoryController:uploadCategoryImage EXECUTION ENDED. traceId: {}",
        TraceIdHolder.getTraceId());

    return ResponseEntity.ok().headers(headers).build();
  }
}
