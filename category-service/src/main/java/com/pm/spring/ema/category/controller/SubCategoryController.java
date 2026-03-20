package com.pm.spring.ema.category.controller;

import com.pm.spring.ema.category.service.SubCategoryService;
import com.pm.spring.ema.category.utils.TraceIdHolder;
import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.StatusType;
import com.pm.spring.ema.common.util.api.SuccessResponse;
import com.pm.spring.ema.common.util.dto.SubCategoryDto;
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
@RequestMapping("api/sub-category")
@Tag(name = "sub-category")
public class SubCategoryController {
  private final SubCategoryService subCategoryService;

  @Operation(summary = "Create a new sub-category", description = "Add a new sub-category")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Sub-category created",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SubCategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.CONFLICT,
            description = "Sub-category already exist",
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
  public ResponseEntity<SuccessResponse> createSubCategory(
      @RequestBody @Valid final SubCategoryDto subCategoryCreateRequestDto) throws FoundException {
    log.debug(
        "SubCategoryController:createSubCategory EXECUTION STARTED. traceId: {} request payload: {}",
        TraceIdHolder.getTraceId(),
        subCategoryCreateRequestDto);

    SubCategoryDto subCategoryCreateResponseDto =
        subCategoryService.createSubCategory(subCategoryCreateRequestDto);
    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "SubCategoryController:createSubCategory EXECUTION ENDED. traceId: {} response: {}",
        TraceIdHolder.getTraceId(),
        subCategoryCreateResponseDto);

    return ResponseEntity.status(HttpStatus.CREATED)
        .headers(headers)
        .body(
            SuccessResponse.builder()
                .data(subCategoryCreateResponseDto)
                .status(StatusType.SUCCESS)
                .build());
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Sub-category found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SubCategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Sub-category could not found",
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
  @Operation(summary = "Get a sub-category by id", description = "Get a sub-category by id")
  @GetMapping("{id}")
  public ResponseEntity<SubCategoryDto> getSubCategoryById(@PathVariable final UUID id) {

    log.debug(
        "SubCategoryController:getSubCategoryById EXECUTION STARTED. traceId: {} request id: {}",
        TraceIdHolder.getTraceId(),
        id);

    SubCategoryDto subCategoryFindResponseDto = subCategoryService.getSubCategoryById(id);
    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "SubCategoryController:getSubCategoryById EXECUTION ENDED. traceId: {} response : {}",
        TraceIdHolder.getTraceId(),
        subCategoryFindResponseDto);

    return ResponseEntity.ok().headers(headers).body(subCategoryFindResponseDto);
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Sub-categories found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SubCategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Sub-categories could not found",
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
  @Operation(summary = "Get all sub-categories", description = "Fetch all the sub-categories")
  @GetMapping("list")
  public ResponseEntity<List<SubCategoryDto>> getAllSubCategory() {

    log.debug(
        "SubCategoryController:getAllSubCategory EXECUTION STARTED. traceId: {}",
        TraceIdHolder.getTraceId());

    List<SubCategoryDto> subCategoryFindResponseDtoList = subCategoryService.getAllSubCategory();

    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "SubCategoryController:getAllSubCategory EXECUTION ENDED. traceId: {} response : {}",
        TraceIdHolder.getTraceId(),
        subCategoryFindResponseDtoList);
    return ResponseEntity.ok().headers(headers).body(subCategoryFindResponseDtoList);
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Sub-categories updated",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SubCategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Sub-categories could not found",
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
  @Operation(summary = "Update a Sub-category by id", description = "Update a Sub-category")
  @PutMapping("update/{id}")
  public ResponseEntity<SuccessResponse> updateSubCategory(
      @PathVariable UUID id, @RequestBody SubCategoryDto subCategoryRequestDto) {

    log.debug(
        "SubCategoryController:updateSubCategory EXECUTION STARTED. traceId: {} request id: {} payload: {}",
        TraceIdHolder.getTraceId(),
        id,
        subCategoryRequestDto);

    SubCategoryDto subCategoryUpdateResponseDto =
        subCategoryService.updateSubCategory(id, subCategoryRequestDto);

    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "SubCategoryController:updateSubCategory EXECUTION ENDED. traceId: {} response: {}",
        TraceIdHolder.getTraceId(),
        subCategoryUpdateResponseDto);
    return ResponseEntity.ok()
        .headers(headers)
        .body(
            SuccessResponse.builder()
                .data(subCategoryUpdateResponseDto)
                .status(StatusType.SUCCESS)
                .build());
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Sub-category deleted.",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SubCategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Sub-category could not found",
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
  @Operation(summary = "Delete a Sub-category by id", description = "Delete a Sub-category")
  @DeleteMapping("delete/{id}")
  public ResponseEntity<SuccessResponse> deleteSubCategoryById(@PathVariable final UUID id) {

    log.debug(
        "SubCategoryController:deleteSubCategoryById EXECUTION STARTED. traceId: {} request id: {}",
        TraceIdHolder.getTraceId(),
        id);

    SubCategoryDto categoryDeleteResponseDto = subCategoryService.deleteSubCategoryById(id);

    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "SubCategoryController:deleteSubCategoryById EXECUTION ENDED. traceId: {}",
        TraceIdHolder.getTraceId());
    return ResponseEntity.ok()
        .headers(headers)
        .body(
            SuccessResponse.builder()
                .data(categoryDeleteResponseDto)
                .status(StatusType.SUCCESS)
                .build());
  }

  @Operation(summary = "Sort and Filter for sub-category list page")
  @GetMapping("findByPage/{pageNumber}")
  public List<SubCategoryDto> findByPage(
      @PathVariable("pageNumber") int pageNumber,
      @RequestParam("sortField") String sortField,
      @RequestParam("sortDir") String sortDir,
      @RequestParam("keyword") String keyword) {

    return subCategoryService.findByPage(pageNumber, sortField, sortDir, keyword);
  }

  @Operation(summary = "Upload Image of sub-category ")
  @PostMapping(value = "upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<HttpStatus> uploadSubCategoryImage(
      @RequestParam @NotNull final UUID id,
      @NotNull @RequestParam(name = "fileImage", value = "fileImage")
          final MultipartFile multipartFile)
      throws IOException, NotFoundException {

    log.debug(
        "SubCategoryController:uploadSubCategoryImage EXECUTION STARTED. traceId: {} request id: {}",
        TraceIdHolder.getTraceId(),
        id);

    if (!subCategoryService.isSubCategoryExistById(id)) {
      throw new NotFoundException(ErrorCodes.E1513, id.toString());
    }

    if (!multipartFile.isEmpty()) {
      String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

      String uploadDir = "spring6-ecommerce-category/sub-category-images";
      FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());

      subCategoryService.updateSubCategoryImageById(id, fileName);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "SubCategoryController:uploadSubCategoryImage EXECUTION ENDED. traceId: {}",
        TraceIdHolder.getTraceId());

    return ResponseEntity.ok().headers(headers).build();
  }

  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Sub-categories found",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = SubCategoryDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Sub-categories could not found",
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
  @Operation(
      summary = "Get all sub-categories associated with one categoryId ",
      description = "Fetch all the sub-categories associated with one categoryId")
  @GetMapping(value = "{categoryId}", produces = "application/json")
  public ResponseEntity<List<SubCategoryDto>> getSubCategoriesByCategoryId(
      @PathVariable final UUID categoryId) {

    log.debug(
        "SubCategoryController:getSubCategoriesByCategoryId EXECUTION STARTED. traceId: {} request id: {}",
        TraceIdHolder.getTraceId(),
        categoryId);

    List<SubCategoryDto> subCategoryFindResponseDtoList =
        subCategoryService.getSubCategoriesByCategoryId(categoryId);

    HttpHeaders headers = new HttpHeaders();
    headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

    log.debug(
        "SubCategoryController:getSubCategoriesByCategoryId EXECUTION ENDED. traceId: {} response : {}",
        TraceIdHolder.getTraceId(),
        subCategoryFindResponseDtoList);
    return ResponseEntity.ok().headers(headers).body(subCategoryFindResponseDtoList);
  }
}
