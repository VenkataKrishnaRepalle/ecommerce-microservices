package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.commondto.BrandDto;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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

    /**
     * @return List<BrandDto>
     */
    @GetMapping("list")
    public List<BrandDto> listAll() {
        System.out.println(categoryServiceClient.listAll());
        return brandService.listAll();
    }
    @GetMapping("{brandId}")
    public BrandDto getById(@PathVariable final UUID brandId) {
        return brandService.getById(brandId);
    }

    /**
     * @param brandDto
     * @param multipartFile
     * @return ResponseEntity<HttpHeaders>
     * @throws IOException
     */
    @PostMapping("save")
    public ResponseEntity<HttpHeaders> saveBrand(
            @RequestBody @Valid final BrandDto brandDto,
            @RequestParam("fileImage") final MultipartFile multipartFile)
            throws IOException {

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

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    public BrandDto update(@RequestBody BrandDto brandDto) {
        return BrandDto.builder().build();
    }



    @DeleteMapping("{brandId}")
    public void deleteById(@PathVariable final UUID brandId) {
        brandService.deleteById(brandId);
    }

}
