package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.common.dto.SubCategoryFindResponseDto;
import com.spring6.ecommerce.common.utils.FileUploadUtils;
import com.spring6.ecommerce.dto.SubCategoryCreateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryCreateResponseDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.SubCategoryUpdateResponseDto;
import com.spring6.ecommerce.entity.SubCategory;
import com.spring6.ecommerce.exception.SubCategoryNameAlreadyExistException;
import com.spring6.ecommerce.exception.SubCategoryNotFoundException;
import com.spring6.ecommerce.service.SubCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("sub-category")
@Tag(name = "SubCategory")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    @Operation(summary = "Create new sub-category")
    @PostMapping("create")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid final SubCategoryCreateRequestDto subCategoryCreateRequestDto) throws SubCategoryNameAlreadyExistException {
        if (subCategoryService.isSubCategoryExistByName(subCategoryCreateRequestDto.getName())) {
            throw new SubCategoryNameAlreadyExistException("Sub-Category name :" + subCategoryCreateRequestDto.getName() + " already exist");
        }

        SubCategoryCreateResponseDto saveCategory = subCategoryService.create(subCategoryCreateRequestDto);

        return new ResponseEntity(saveCategory, HttpStatus.CREATED);
    }

    @Operation(summary = "List all sub-category")
    @GetMapping("list")
    public List<SubCategoryFindResponseDto> findAll() {
        return subCategoryService.findAll();
    }

    @Operation(summary = "List sub-category by id")
    @GetMapping("{id}")
    public SubCategoryFindResponseDto findById(@PathVariable final UUID id) {
        return subCategoryService.findById(id);
    }

    @Operation(summary = "Update sub-category")
    @PutMapping("update/{id}")
    public SubCategoryUpdateResponseDto update(@PathVariable UUID id, @RequestBody SubCategoryUpdateRequestDto categoryDto) {
        return subCategoryService.update(id, categoryDto);
    }

    @Operation(summary = "delete sub-category")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable final UUID id) {
        subCategoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Sort and Filter for sub-category list page")
    @GetMapping("findByPage/{pageNumber}")
    public List<SubCategoryFindResponseDto> findByPage(@PathVariable("pageNumber") int pageNumber,
                                                       @RequestParam("sortField") String sortField,
                                                       @RequestParam("sortDir") String sortDir,
                                                       @RequestParam("keyword") String keyword) {

        return subCategoryService.findByPage(pageNumber, sortField, sortDir, keyword);

    }

    @Operation(summary = "Upload Image of sub-category ")
    @PostMapping(value = "/upload-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> uploadImage(@RequestParam @NotNull final UUID id,
                                                  @NotNull @RequestParam(name = "fileImage", value = "fileImage") final MultipartFile multipartFile)
            throws IOException, SubCategoryNotFoundException {
        if (!subCategoryService.isSubCategoryExistById(id)) {
            throw new SubCategoryNotFoundException("Sub-Category does not exist  with id " + id);
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uploadDir = "spring6-ecommerce-category/sub-category-images";
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());

            subCategoryService.updateFileNameById(id, fileName);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }
    @Operation(summary = "Get all sub-categories associated with One categoryId ")
    @GetMapping(value = "{categoryId}",produces = "application/json")
    public List<SubCategoryFindResponseDto> getSubCategoriesByCategoryId(@PathVariable final UUID categoryId) {
        return subCategoryService.findByCategoryId(categoryId);
    }

}
