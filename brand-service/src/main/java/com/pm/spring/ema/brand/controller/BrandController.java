package com.pm.spring.ema.brand.controller;

import com.pm.spring.ema.brand.dto.request.BrandCreateRequestDto;
import com.pm.spring.ema.brand.dto.response.BrandCreateResponseDto;
import com.pm.spring.ema.brand.dto.request.BrandUpdateRequestDto;
import com.pm.spring.ema.brand.dto.response.BrandUpdateResponseDto;
import com.pm.spring.ema.brand.dto.enums.BrandSearchKeywordEnum;
import com.pm.spring.ema.brand.exception.BrandNameAlreadyExistException;
import com.pm.spring.ema.brand.exception.BrandNotFoundException;
import com.pm.spring.ema.brand.service.BrandService;
import com.pm.spring.ema.brand.utils.TraceIdHolder;
import com.pm.spring.ema.brand.validations.ValidImageExtension;
import com.pm.spring.ema.common.dto.BrandFindResponseDto;
import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.common.util.exception.ErrorListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/brand")
public class BrandController {

    private final BrandService brandService;

    @Value("${file.upload_directory}")
    private String IMAGE_UPLOAD_DIRECTORY;

    @Operation(tags = "Brand", summary = "Create Brand", description = "Create a new Brand by entering brand details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Brand", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "create")
    public ResponseEntity<BrandCreateResponseDto> createBrand(
            @RequestBody @Valid final BrandCreateRequestDto brandCreateRequestDto)
            throws BrandNameAlreadyExistException {
        log.debug("BrandController:createBrand STARTED. traceId: {} request: {}", TraceIdHolder.getTraceId(), brandCreateRequestDto);

        BrandCreateResponseDto savedBrandDto = brandService.create(brandCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("BrandController:createBrand ENDED traceId: {} response: {}", TraceIdHolder.getTraceId(), savedBrandDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(savedBrandDto);
    }

    @Operation(tags = "Brand", summary = "Get Brand By Id", description = "Get Brand by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get Brand Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Brand not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<BrandFindResponseDto> getBrandById(@PathVariable @Valid final UUID id) {
        log.info("BrandController:getBrandById STARTED. traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        BrandFindResponseDto brandFindResponseDto = brandService.getById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("BrandController:getBrandById ENDED. traceId: {} response : {}", TraceIdHolder.getTraceId(), brandFindResponseDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(brandFindResponseDto);
    }

    @Operation(tags = "Brand", summary = "Get All Brands", description = "Get all brands by passing brand id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get Brand Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Brand not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("list")
    public ResponseEntity<List<BrandFindResponseDto>> getAllBrands() {
        log.info("BrandController:getAllBrands STARTED traceId: {}", TraceIdHolder.getTraceId());
        List<BrandFindResponseDto> brandFindResponseDtoList = brandService.getAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("BrandController:getAllBrands ENDED traceId: {} response: {}", TraceIdHolder.getTraceId(), brandFindResponseDtoList);

        return ResponseEntity.ok()
                .headers(headers)
                .body(brandFindResponseDtoList);
    }

    @Operation(tags = "Brand", summary = "Update Brand", description = "Update brand by passing brand id and brand request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Brand", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("update/{id}")
    public ResponseEntity<BrandUpdateResponseDto> updateBrand(@PathVariable UUID id, @RequestBody BrandUpdateRequestDto brandUpdateRequestDto) {

        log.debug("BrandController:updateBrand STARTED traceId: {} request id: {} payload: {}", TraceIdHolder.getTraceId(), id, brandUpdateRequestDto);

        BrandUpdateResponseDto brandUpdateResponseDto = brandService.update(id, brandUpdateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("BrandController:updateBrand ENDED traceId: {} response: {}", TraceIdHolder.getTraceId(), brandUpdateResponseDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(brandUpdateResponseDto);

    }

    @Operation(tags = "Brand", summary = "Delete Brand By Id", description = "Delete existing brand by passing brand id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Brand", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteBrandById(@PathVariable final UUID id) {
        log.info("BrandController:deleteById STARTED traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        brandService.deleteById(id);
        String dir = "../brand-logos/" + id;
        FileUploadUtils.removeDir(dir);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("BrandController:deleteById ENDED traceId: {}", TraceIdHolder.getTraceId());

        return ResponseEntity.noContent()
                .headers(headers)
                .build();
    }

    @Operation(tags = "Brand", summary = "Get Brands By Pagination", description = "Get brands by pagination by passing pagination attributes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Brands", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("page")
    public ResponseEntity<List<BrandFindResponseDto>> getBrandsByPage(@RequestParam(name = "pageNumber") Integer pageNumber,
                                                                      @RequestParam("perPageCount") Integer perPageCount,
                                                                      @RequestParam("sortField") String sortField,
                                                                      @RequestParam("sortDirection") String sortDirection,
                                                                      @RequestParam("searchField") BrandSearchKeywordEnum searchField,
                                                                      @RequestParam("searchKeyword") String searchKeyword) {
        log.info("BrandController:getBrandsByPage STARTED traceId: {} request pageNumber: {} perPageCount: {} sortField: {} sortDirection: {} searchField: {} searchKeyword: {}", TraceIdHolder.getTraceId(), pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        List<BrandFindResponseDto> brandFindResponseDtoList = brandService.getByPage(pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("BrandController:getBrandsByPage ENDED traceId: {} response: {}", TraceIdHolder.getTraceId(), brandFindResponseDtoList);

        return ResponseEntity.ok()
                .headers(headers)
                .body(brandFindResponseDtoList);
    }

    @Operation(tags = "Brand", summary = "Upload Brand Image", description = "Upload brand image by passing brand id and brand image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Image uploaded success"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("upload-image")
    public ResponseEntity<Void> uploadBrandImage(
            @RequestParam @NotNull final UUID brandId,
            @NotNull @ValidImageExtension @RequestParam("fileImage") final MultipartFile multipartFile)
            throws IOException, BrandNameAlreadyExistException {
        log.info("BrandController:uploadBrandImage STARTED traceId: {} request id: {}", TraceIdHolder.getTraceId(), brandId);

        if (brandService.isIdExist(brandId)) {
            log.error("BrandController:uploadBrandImage traceId: {} Brand Not Found id: {}", TraceIdHolder.getTraceId(), brandId);
            throw new BrandNotFoundException(ErrorCodes.E0507, brandId.toString());
        }

        if (!multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            FileUploadUtils.cleanDir(IMAGE_UPLOAD_DIRECTORY);
            FileUploadUtils.saveFile(IMAGE_UPLOAD_DIRECTORY, fileName, multipartFile.getInputStream());

            brandService.updateImageName(brandId, fileName);

        } else {
            log.error("BrandController:uploadBrandImage ERROR traceId: {} File Not Found id: {}", TraceIdHolder.getTraceId(), brandId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("BrandController:uploadBrandImage ENDED traceId: {}", TraceIdHolder.getTraceId());

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @Operation(tags = "Brand", summary = "Get Brand Image By Id", description = "Get brand by passing brand id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Success", content = {@Content(mediaType = "application/octet-stream")}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Image Not found"),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("image/{id}")
    public ResponseEntity<Resource> getBrandImageById(
            @PathVariable("id") UUID id) throws MalformedURLException {
        log.debug("BrandController:getBrandImageById STARTED traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        String imageName = brandService.getImageNameById(id);

        Path imagePath = Paths.get(IMAGE_UPLOAD_DIRECTORY, imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        if (!imageResource.exists()) {
            log.error("BrandController:getBrandImageById ENDED traceId: {} id: {} Image source not found", TraceIdHolder.getTraceId(), id);
            return ResponseEntity.notFound()
                    .headers(headers).build();

        }
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageName + "\"");

        log.info("BrandController:getBrandImageById ENDED traceId: {} id: {}", TraceIdHolder.getTraceId(), id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .headers(headers)
                .body(imageResource);

    }

}
