package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.commondto.BrandFineResponesDto;
import com.spring6.ecommerce.dto.BrandCreateRequestDto;
import com.spring6.ecommerce.dto.BrandCreateResponseDto;
import com.spring6.ecommerce.dto.BrandUpdateRequestDto;
import com.spring6.ecommerce.dto.BrandUpdateResponseDto;
import com.spring6.ecommerce.feign.CategoryServiceFeignClient;
import com.spring6.ecommerce.service.BrandService;
import com.spring6.ecommerce.utils.FileUploadUtils;
import jakarta.validation.Valid;
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

    @GetMapping("list")
    public List<BrandFineResponesDto> listAll() {
        System.out.println(categoryServiceClient.listAll());
        return brandService.listAll();
    }

    @GetMapping("{brandId}")
    public BrandFineResponesDto getById(@PathVariable final UUID brandId) {
        return brandService.getById(brandId);
    }

    @PostMapping("save")
    public ResponseEntity<HttpHeaders> saveBrand(
            @RequestBody @Valid final BrandCreateRequestDto brandCreateRequestDto,
            @RequestParam("fileImage") final MultipartFile multipartFile)
            throws IOException {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            brandCreateRequestDto.setLogo(fileName);

            String uploadDir = "../brand-logos";

            FileUploadUtils.cleanDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
        }
        BrandCreateResponseDto savedBrandDto = brandService.save(brandCreateRequestDto);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/brand" + savedBrandDto.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    public BrandUpdateResponseDto update(@RequestBody BrandUpdateRequestDto brandDto) {
        return BrandUpdateResponseDto.builder().build();
    }

    @DeleteMapping("{brandId}")
    public void deleteById(@PathVariable final UUID brandId) {
        brandService.deleteById(brandId);
    }

}
