package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.dto.BrandDto;
import com.spring6.ecommerce.service.BrandService;
import com.spring6.ecommerce.utils.FileUploadUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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

    @GetMapping("list")
    public List<BrandDto> listAll() {
        return brandService.listAll();
    }

    @PostMapping("save")
    public ResponseEntity save(@RequestBody @Valid BrandDto brandDto, @RequestParam("fileImage") MultipartFile multipartFile) throws IOException {

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            brandDto.setLogo(fileName);

            String uploadDir = "../brand-logos";

            FileUploadUtils.cleanDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
        }
        BrandDto savedBrandDto = brandService.save(brandDto);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/brand" + savedBrandDto.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @GetMapping("{brandId}")
    public BrandDto getById(@PathVariable UUID brandId) {
        return brandService.getById(brandId);
    }

    @DeleteMapping("{brandId}")
    public void deleteById(@PathVariable UUID brandId) {
        brandService.deleteById(brandId);
    }

}
