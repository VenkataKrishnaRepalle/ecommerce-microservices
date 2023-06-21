package com.spring6.controller;

import com.spring6.common.dto.BrandFindResponesDto;
import com.spring6.common.utils.FileUploadUtils;
import com.spring6.dto.*;
import com.spring6.enums.BrandSearchKeywordEnum;
import com.spring6.exception.BrandNameAlreadyExistException;
import com.spring6.feign.CategoryServiceFeignClient;
import com.spring6.service.BrandService;
import com.spring6.validations.ValidImageExtension;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/brand")
public class BrandController {

    private final BrandService brandService;

    @Value("${file.upload-directory}")
    private String IMAGE_UPLOAD_DIRECTORY;

    @Autowired
    private CategoryServiceFeignClient categoryServiceClient;

    @Operation(
            summary = "Create a Brand",
            description = "Create a new Brand by entering brand details",
            tags = "Brand"
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Create a Brand",
                    content = {
                            @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = BrandCreateRequestDto.class)
                            )
                    }
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "Some data already exist",
                    content = @Content
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal Server error",
                    content = @Content
            )
    })

    @PostMapping(value = "create")
    public ResponseEntity<HttpStatus> createBrand(
            @RequestBody @Valid final BrandCreateRequestDto brandCreateRequestDto)
            throws BrandNameAlreadyExistException {

        if (brandService.isNameExist(brandCreateRequestDto.getName())) {
            throw new BrandNameAlreadyExistException("Brand name :" + brandCreateRequestDto.getName() + " already exist");
        }

        BrandCreateResponseDto savedBrandDto = brandService.create(brandCreateRequestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("{brandId}")
    public BrandFindResponesDto getBrandById(@PathVariable final UUID brandId) {
        return brandService.findById(brandId);
    }

    @GetMapping("list")
    public List<BrandFindResponesDto> getAllBrands() {
        return brandService.findAll();
    }

    @PatchMapping("update/{id}")
    public BrandUpdateResponseDto updateBrand(@PathVariable UUID id, @RequestBody BrandUpdateRequestDto brandDto) {
        return brandService.update(id, brandDto);
    }

    @DeleteMapping("delete/{brandId}")
    public void deleteById(@PathVariable final UUID brandId) {
        brandService.deleteById(brandId);
        String dir = "../brand-logos/" + brandId;
        FileUploadUtils.removeDir(dir);
    }



    @GetMapping("page")
    public List<BrandFindResponesDto> getBrandsByPage(@RequestParam(name = "pageNumber") Integer pageNumber,
                                                 @RequestParam("perPageCount") Integer perPageCount,
                                                 @RequestParam("sortField") String sortField,
                                                 @RequestParam("sortDirection") String sortDir,
                                                 @RequestParam("searchField") BrandSearchKeywordEnum searchField,
                                                 @RequestParam("searchKeyword") String searchKeyword) {
        return brandService.findByPage(pageNumber, perPageCount, sortField, sortDir,searchField, searchKeyword);
    }
    @PostMapping("upload-image")
    public ResponseEntity<?> uploadBrandImage(
            @RequestParam @NotNull final UUID brandId,
            @NotNull @ValidImageExtension @RequestParam(
                    name = "fileImage",
                    value = "fileImage") final MultipartFile multipartFile)
            throws IOException, BrandNameAlreadyExistException {

        if (brandService.isIdExist(brandId)) {
            throw new BrandNameAlreadyExistException("Brand Not found for brandId :" + brandId);
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            FileUploadUtils.cleanDir(IMAGE_UPLOAD_DIRECTORY);
            FileUploadUtils.saveFile(IMAGE_UPLOAD_DIRECTORY, fileName, multipartFile.getInputStream());

            brandService.updateImageName(brandId, fileName);

        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getBrandImageById(
            @PathVariable("id") UUID id) throws MalformedURLException {

        BrandFindResponesDto brandDto = brandService.findById(id);
        String imageDirectory = IMAGE_UPLOAD_DIRECTORY + brandDto.getLogo();

//        Optional<String> extensionOptional = FileUploadUtils.getExtensionByString(brandDto.getLogo());

//        String extension =  extensionOptional.get();

//        MediaType contentType;
//        if (extension.equals("png")) {
//            contentType = MediaType.IMAGE_PNG;
//        } else if(extension.equals("jpg") || extension.equals("jpeg")) {
//            contentType = MediaType.IMAGE_JPEG;
//        } else {
//            throw new UnsupportedFileFormatException("File parse error");
//        }

//        InputStream in = getClass().getResourceAsStream(imageDirectory);
//        return ResponseEntity.ok()
//                .contentType(contentType)
//                .body(new InputStreamResource(in));


        Path imagePath = Paths.get(IMAGE_UPLOAD_DIRECTORY, brandDto.getLogo());
        Resource imageResource = new UrlResource(imagePath.toUri());

        // Check if the image file exists
        if (imageResource.exists()) {
            // Return the image for download
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + brandDto.getLogo() + "\"")
                    .body(imageResource);
        } else {
            // Return an error response if the image file is not found
            return ResponseEntity.notFound().build();
        }

    }




}
