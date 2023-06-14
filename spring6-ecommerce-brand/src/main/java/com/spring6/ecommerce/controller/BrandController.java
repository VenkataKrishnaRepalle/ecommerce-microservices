package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.common.dto.BrandFindResponesDto;
import com.spring6.ecommerce.common.utils.FileUploadUtils;
import com.spring6.ecommerce.dto.BrandCreateRequestDto;
import com.spring6.ecommerce.dto.BrandCreateResponseDto;
import com.spring6.ecommerce.dto.BrandUpdateRequestDto;
import com.spring6.ecommerce.dto.BrandUpdateResponseDto;
import com.spring6.ecommerce.exception.BrandNameAlreadyExistException;
import com.spring6.ecommerce.feign.CategoryServiceFeignClient;
import com.spring6.ecommerce.service.BrandService;
import com.spring6.ecommerce.validations.ValidImageExtension;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("brand")
public class BrandController {

    private final BrandService brandService;

    private final String IMAGE_UPLOAD_DIRECTORY = "spring6-ecommerce-brand/brand-logos/";

    @Autowired
    private CategoryServiceFeignClient categoryServiceClient;

    @GetMapping("page/{pageNumber}")
    public List<BrandFindResponesDto> findByPage(@PathVariable(name = "pageNumber") int pageNumber,
                                                 @RequestParam("sortField") String sortField,
                                                 @RequestParam("sortDir") String sortDir,
                                                 @RequestParam("keyword") String keyword) {
        return brandService.findByPage(pageNumber, sortField, sortDir, keyword);
    }

    @GetMapping("image/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getImageById(@PathVariable("id") UUID id) throws MalformedURLException {

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
            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + brandDto.getLogo() + "\"")
                    .body(imageResource);
        } else {
            // Return an error response if the image file is not found
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("list")
    public List<BrandFindResponesDto> findAll() {
        return brandService.findAll();
    }

    @GetMapping("{brandId}")
    public BrandFindResponesDto getById(@PathVariable final UUID brandId) {
        return brandService.findById(brandId);
    }

    @PostMapping(value = "create")
    public ResponseEntity<HttpStatus> create(
            @RequestBody @Valid final BrandCreateRequestDto brandCreateRequestDto)
            throws BrandNameAlreadyExistException {

        if (brandService.isNameExist(brandCreateRequestDto.getName())) {
            throw new BrandNameAlreadyExistException("Brand name :" + brandCreateRequestDto.getName() + " already exist");
        }

        BrandCreateResponseDto savedBrandDto = brandService.create(brandCreateRequestDto);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/upload-image")
    public ResponseEntity<?> uploadImage(
            @RequestParam @NotNull final UUID brandId,
            @NotNull @ValidImageExtension @RequestParam(name = "fileImage", required = true, value = "fileImage") final MultipartFile multipartFile)
            throws IOException, BrandNameAlreadyExistException {

        if (brandService.isIdExist(brandId)) {
            throw new BrandNameAlreadyExistException("Brand Not found for brandId :" + brandId);
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uploadDir = "spring6-ecommerce-brand/brand-logos";

            FileUploadUtils.cleanDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());

            brandService.updateImageName(brandId, fileName);

        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }



    @PatchMapping("update/{id}")
    public BrandUpdateResponseDto update(@PathVariable UUID id, @RequestBody BrandUpdateRequestDto brandDto) {
        return BrandUpdateResponseDto.builder().build();
    }

    @DeleteMapping("delete/{brandId}")
    public void deleteById(@PathVariable final UUID brandId) {
        brandService.deleteById(brandId);
        String dir = "../brand-logos/" + brandId;
        FileUploadUtils.removeDir(dir);
    }

}
