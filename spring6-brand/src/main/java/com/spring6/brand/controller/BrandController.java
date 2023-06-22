package com.spring6.brand.controller;

import com.spring6.brand.dto.BrandCreateRequestDto;
import com.spring6.brand.dto.BrandCreateResponseDto;
import com.spring6.brand.dto.BrandUpdateRequestDto;
import com.spring6.brand.dto.BrandUpdateResponseDto;
import com.spring6.brand.exception.BrandNotFoundException;
import com.spring6.brand.service.BrandService;
import com.spring6.common.dto.BrandFindResponseDto;
import com.spring6.common.exeption.ErrorCode;
import com.spring6.common.exeption.ErrorListResponse;
import com.spring6.common.exeption.ErrorResponse;
import com.spring6.common.utils.FileUploadUtils;
import com.spring6.brand.enums.BrandSearchKeywordEnum;
import com.spring6.brand.exception.BrandNameAlreadyExistException;
import com.spring6.brand.validations.ValidImageExtension;
import com.spring6.common.utils.GlobalConstants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
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
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/brand")
public class BrandController {
    private String traceId = MDC.get(GlobalConstants.TRACE_ID);

    private final BrandService brandService;

    @Value("${file.upload-directory}")
    private String IMAGE_UPLOAD_DIRECTORY;

    @Operation(
            summary = "Create Brand",
            description = "Create a new Brand by entering brand details",
            tags = "Brand"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create a Brand", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandCreateResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = "409", description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @PostMapping(value = "create")
    public ResponseEntity<BrandCreateResponseDto> createBrand(
            @RequestBody @Valid final BrandCreateRequestDto brandCreateRequestDto)
            throws BrandNameAlreadyExistException {
        log.info("BrandController:createBrand execution started.");
        log.info("BrandController:createBrand traceId: {} request payload: {}", traceId, brandCreateRequestDto);

        BrandCreateResponseDto savedBrandDto = brandService.create(brandCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, traceId);

        log.info("BrandController:createBrand traceId: {} response: {}", traceId, savedBrandDto);
        log.info("BrandController:createBrand execution ended.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(savedBrandDto);
    }
    @Operation(
            summary = "Get Brand By Id",
            description = "get Brand by id",
            tags = "Brand"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Brand Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandFindResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<BrandFindResponseDto> getBrandById(@PathVariable @Valid final UUID id) {
        log.info("BrandController:getBrandById execution started.");
        log.info("BrandController:getBrandById traceId: {} request id: {}", traceId, id);

        BrandFindResponseDto brandFindResponseDto =  brandService.findById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, traceId);

        log.info("BrandController:getBrandById traceId: {} response : {}", traceId, brandFindResponseDto);
        log.info("BrandController:getBrandById execution ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(brandFindResponseDto);
    }

    @Operation(
            summary = "Get All Brands",
            description = "Get all brands by passing brand id",
            tags = "Brand"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get Brand Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandFindResponseDto.class))}),
            @ApiResponse(responseCode = "404", description = "Brand not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("list")
    public ResponseEntity<List<BrandFindResponseDto>> getAllBrands() {
        log.info("BrandController:getAllBrands started.");
        log.info("BrandController:getAllBrands traceId: {}", traceId);
        List<BrandFindResponseDto> brandFindResponseDtoList =  brandService.findAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, traceId);

        log.info("BrandController:getAllBrands traceId: {} response: {}", traceId, brandFindResponseDtoList);
        log.info("BrandController:getAllBrands execution ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(brandFindResponseDtoList);
    }

    @Operation(
            summary = "Update Brand",
            description = "Update brand by passing brand id and brand request body",
            tags = "Brand"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create a Brand", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandCreateResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = "409", description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @PatchMapping("update/{id}")
    public ResponseEntity<BrandUpdateResponseDto> updateBrand(@PathVariable UUID id, @RequestBody BrandUpdateRequestDto brandUpdateRequestDto) {

        log.info("BrandController:updateBrand started.");
        log.info("BrandController:updateBrand traceId: {} request id: {} payload: {}", traceId, id, brandUpdateRequestDto);

        BrandUpdateResponseDto brandUpdateResponseDto = brandService.update(id, brandUpdateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, traceId);

        log.info("BrandController:updateBrand traceId: {} response: {}", traceId, brandUpdateResponseDto);
        log.info("BrandController:updateBrand ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(brandUpdateResponseDto);

    }

    @Operation(
            summary = "Delete Brand By Id",
            description = "Delete existing brand by passing brand id",
            tags = "Brand"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Create a Brand", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandCreateResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = "409", description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final UUID id) {
        log.info("BrandController:deleteById started.");
        log.info("BrandController:deleteById traceId: {} request id: {}", traceId, id);

        brandService.deleteById(id);
        String dir = "../brand-logos/" + id;
        FileUploadUtils.removeDir(dir);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, traceId);

        log.info("BrandController:deleteById traceId: {}", traceId);
        log.info("BrandController:deleteById ended.");

        return ResponseEntity.noContent()
                .headers(headers)
                .build();
    }

    @Operation(
            summary = "Get Brands By Pagination",
            description = "Get brands by pagination by passing pagination attributes",
            tags = "Brand"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Brands", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandFindResponseDto.class))}),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @GetMapping("page")
    public ResponseEntity<List<BrandFindResponseDto>> getBrandsByPage(@RequestParam(name = "pageNumber") Integer pageNumber,
                                                                      @RequestParam("perPageCount") Integer perPageCount,
                                                                      @RequestParam("sortField") String sortField,
                                                                      @RequestParam("sortDirection") String sortDirection,
                                                                      @RequestParam("searchField") BrandSearchKeywordEnum searchField,
                                                                      @RequestParam("searchKeyword") String searchKeyword) {
        log.info("BrandController:getBrandsByPage started.");
        log.info("BrandController:getBrandsByPage traceId: {} request pageNumber: {} perPageCount: {} sortField: {} sortDirection: {} searchField: {} searchKeyword: {}", traceId, pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        List<BrandFindResponseDto> brandFindResponseDtoList =  brandService.findByPage(pageNumber, perPageCount, sortField, sortDirection,searchField, searchKeyword);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, traceId);

        log.info("BrandController:getBrandsByPage traceId: {} response: {}", traceId, brandFindResponseDtoList);
        log.info("BrandController:getBrandsByPage ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(brandFindResponseDtoList);
    }
    @Operation(
            summary = "Upload Brand Image",
            description = "Upload brand image by passing brand id and brand image",
            tags = "Brand"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Image uploaded success"),
            @ApiResponse(responseCode = "404", description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })

    @PostMapping("upload-image")
    public ResponseEntity<Void> uploadBrandImage(
            @RequestParam @NotNull final UUID brandId,
            @NotNull @ValidImageExtension @RequestParam("fileImage") final MultipartFile multipartFile)
            throws IOException, BrandNameAlreadyExistException {
        log.info("BrandController:uploadBrandImage started.");
        log.info("BrandController:uploadBrandImage traceId: {} request id: {}", traceId, brandId);

        if (brandService.isIdExist(brandId)) {
            log.error("BrandController:uploadBrandImage traceId: {} Brand Not Found id: {}", traceId, brandId);
            throw new BrandNotFoundException(ErrorCode.E0505.getCode(), brandId.toString());
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            FileUploadUtils.cleanDir(IMAGE_UPLOAD_DIRECTORY);
            FileUploadUtils.saveFile(IMAGE_UPLOAD_DIRECTORY, fileName, multipartFile.getInputStream());

            brandService.updateImageName(brandId, fileName);

        } else {
            log.error("BrandController:uploadBrandImage traceId: {} File Not Found id: {}", traceId, brandId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, traceId);

        log.info("BrandController:uploadBrandImage traceId: {}", traceId);
        log.info("BrandController:uploadBrandImage ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @Operation(
            summary = "Get Brand Image By Id",
            description = "Get brand by passing brand id",
            tags = "Brand"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success", content = {@Content(mediaType = "application/octet-stream")}),
            @ApiResponse(responseCode = "404", description = "Image Not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("image/{id}")
    public ResponseEntity<Resource> getBrandImageById(
            @PathVariable("id") UUID id) throws MalformedURLException {
        log.info("BrandController:getBrandImageById started.");
        log.info("BrandController:getBrandImageById traceId: {} request id: {}", traceId, id);

        BrandFindResponseDto brandDto = brandService.findById(id);

        Path imagePath = Paths.get(IMAGE_UPLOAD_DIRECTORY, brandDto.getLogo());
        Resource imageResource = new UrlResource(imagePath.toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, traceId);

        if (!imageResource.exists()) {
            log.error("BrandController:getBrandImageById traceId: {} id: {} Image source not found", traceId, id);
            log.error("BrandController:getBrandImageById ended.");
            return ResponseEntity.notFound()
                    .headers(headers).build();

        }
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + brandDto.getLogo() + "\"");

        log.info("BrandController:getBrandImageById traceId: {} id: {}", traceId, id);
        log.info("BrandController:getBrandImageById ended.");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .headers(headers)
                .body(imageResource);

    }

}
