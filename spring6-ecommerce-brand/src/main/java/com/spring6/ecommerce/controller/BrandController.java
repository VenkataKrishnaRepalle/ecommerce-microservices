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
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("brand")
public class BrandController {

    private final BrandService brandService;

    @Autowired
    private CategoryServiceFeignClient categoryServiceClient;

    @GetMapping("page/{pageNumber}")
    public List<BrandFindResponesDto> findByPage(@PathVariable(name = "pageNumber") int pageNumber,
                                                 @RequestParam("sortField") String sortField,
                                                 @RequestParam("sortDir") String sortDir,
                                                 @RequestParam("keyword") String keyword) {
        return brandService.findByPage(pageNumber, sortField, sortDir, keyword);
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
            @RequestParam(name = "fileImage", required = true, value = "fileImage") final MultipartFile multipartFile)
            throws IOException, BrandNameAlreadyExistException {

        if (brandService.isIdExist(brandId)) {
            throw new BrandNameAlreadyExistException("Brand Not found for brandId :" + brandId);
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uploadDir = "spring6-ecommerce-brand/brand-logos";

            FileUploadUtils.cleanDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());
//            update file name to brand table
            //            brandCreateRequestDto.setLogo(fileName);

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
