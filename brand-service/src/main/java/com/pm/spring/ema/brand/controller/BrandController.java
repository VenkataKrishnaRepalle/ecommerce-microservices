package com.pm.spring.ema.brand.controller;

import com.pm.spring.ema.brand.dto.BrandDto;
import com.pm.spring.ema.brand.service.BrandService;
import com.pm.spring.ema.brand.validations.ValidImageExtension;
import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.dto.BrandFindResponseDto;
import com.pm.spring.ema.common.util.exception.FoundException;
import com.pm.spring.ema.common.util.exception.NotFoundException;
import com.pm.spring.ema.common.util.exception.utils.ErrorCodes;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/brand")
public class BrandController {

  private final BrandService brandService;

  @Value("${file.upload_directory}")
  private String IMAGE_UPLOAD_DIRECTORY;

  @Operation(
      tags = "Brand",
      summary = "Create Brand",
      description = "Create a new Brand by entering brand details")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.CREATED,
            description = "Create a Brand",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BrandDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.BAD_REQUEST,
            description = "Validation failed",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = List.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.CONFLICT,
            description = "Some data already exist",
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
  @PostMapping(value = "create")
  public ResponseEntity<BrandDto> createBrand(@RequestBody @Valid final BrandDto brandDto) {
    BrandDto savedBrandDto = brandService.create(brandDto);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedBrandDto);
  }

  @Operation(tags = "Brand", summary = "Get Brand By Id", description = "Get Brand by id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Get Brand Response",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BrandFindResponseDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Brand not found",
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
  @GetMapping("{id}")
  public ResponseEntity<BrandFindResponseDto> getBrandById(@PathVariable @Valid final UUID id) {
    BrandFindResponseDto brandFindResponseDto = brandService.getById(id);
    return ResponseEntity.ok().body(brandFindResponseDto);
  }

  @Operation(
      tags = "Brand",
      summary = "Get All Brands",
      description = "Get all brands by passing brand id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Get Brand Response",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BrandFindResponseDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Brand not found",
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
  @GetMapping("list")
  public ResponseEntity<List<BrandFindResponseDto>> getAllBrands() {
    List<BrandFindResponseDto> brandFindResponseDtoList = brandService.getAll();

    return ResponseEntity.ok().body(brandFindResponseDtoList);
  }

  @Operation(
      tags = "Brand",
      summary = "Update Brand",
      description = "Update brand by passing brand id and brand request body")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.CREATED,
            description = "Create a Brand",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BrandDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.BAD_REQUEST,
            description = "Validation failed",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = List.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.CONFLICT,
            description = "Some data already exist",
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
  @PatchMapping("update/{id}")
  public ResponseEntity<BrandDto> updateBrand(
      @PathVariable UUID id, @RequestBody BrandDto brandUpdateRequestDto) {
    BrandDto brandUpdateResponseDto = brandService.update(id, brandUpdateRequestDto);
    return ResponseEntity.ok().body(brandUpdateResponseDto);
  }

  @Operation(
      tags = "Brand",
      summary = "Delete Brand By Id",
      description = "Delete existing brand by passing brand id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.CREATED,
            description = "Create a Brand",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BrandDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.BAD_REQUEST,
            description = "Validation failed",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = List.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.CONFLICT,
            description = "Some data already exist",
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
  @DeleteMapping("delete/{id}")
  public ResponseEntity<Void> deleteBrandById(@PathVariable final UUID id) {

    brandService.deleteById(id);
    String dir = "../brand-logos/" + id;
    FileUploadUtils.removeDir(dir);
    return ResponseEntity.noContent().build();
  }

  @Operation(
      tags = "Brand",
      summary = "Get Brands By Pagination",
      description = "Get brands by pagination by passing pagination attributes")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Brands",
            content = {
              @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = BrandFindResponseDto.class))
            }),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @GetMapping("page")
  public ResponseEntity<List<BrandFindResponseDto>> getBrandsByPage(
      @RequestParam(name = "pageNumber") Integer pageNumber,
      @RequestParam("perPageCount") Integer perPageCount,
      @RequestParam("sortField") String sortField,
      @RequestParam("sortDirection") String sortDirection,
      @RequestParam("searchField") String searchField,
      @RequestParam("searchKeyword") String searchKeyword) {
    List<BrandFindResponseDto> brandFindResponseDtoList =
        brandService.getByPage(
            pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);
    return ResponseEntity.ok().body(brandFindResponseDtoList);
  }

  @Operation(
      tags = "Brand",
      summary = "Upload Brand Image",
      description = "Upload brand image by passing brand id and brand image")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Image uploaded success"),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Not found",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = List.class))),
        @ApiResponse(
            responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR,
            description = "Internal server error",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class)))
      })
  @PostMapping("upload-image")
  public ResponseEntity<Void> uploadBrandImage(
      @RequestParam @NotNull final UUID brandId,
      @NotNull @ValidImageExtension @RequestParam("fileImage") final MultipartFile multipartFile)
      throws IOException, FoundException {

    if (brandService.isIdExist(brandId)) {
      throw new NotFoundException(ErrorCodes.E0501, brandId.toString());
    }

    if (!multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
      String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

      FileUploadUtils.cleanDir(IMAGE_UPLOAD_DIRECTORY);
      FileUploadUtils.saveFile(IMAGE_UPLOAD_DIRECTORY, fileName, multipartFile.getInputStream());

      brandService.updateImageName(brandId, fileName);

    } else {
      log.error("BrandController:uploadBrandImage File Not Found id: {}", brandId);
    }

    return ResponseEntity.ok().build();
  }

  @Operation(
      tags = "Brand",
      summary = "Get Brand Image Name By Id",
      description = "Get brand image name by passing brand id")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = HttpStatusCodes.OK,
            description = "Success",
            content = {@Content(mediaType = "application/json")}),
        @ApiResponse(
            responseCode = HttpStatusCodes.NOT_FOUND,
            description = "Image Not found",
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
  @GetMapping("image/{id}")
  public ResponseEntity<Map<String, String>> getBrandImageNameById(@PathVariable UUID id) {

    String imageName = brandService.getImageNameById(id);
    return ResponseEntity.ok().body(Collections.singletonMap("imageName", imageName));
  }
}
