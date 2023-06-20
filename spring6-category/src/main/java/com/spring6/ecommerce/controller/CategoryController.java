package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.common.dto.SubCategoryFindResponseDto;
import com.spring6.ecommerce.common.utils.FileUploadUtils;
import com.spring6.ecommerce.exception.CategoryNameAlreadyExistException;
import com.spring6.ecommerce.exception.CategoryNotFoundException;
import com.spring6.ecommerce.exception.SubCategoryNotFoundException;
import com.spring6.ecommerce.service.CategoryService;
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
@RequestMapping("category")
@Tag(name = "Category")
public class CategoryController {
    private final CategoryService categoryService;

    @Operation(summary = "Create new Category")
    @PostMapping("create")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid final CategoryCreateRequestDto categoryCreateRequestDto) throws CategoryNameAlreadyExistException {
        if (categoryService.isCategoryExistByName(categoryCreateRequestDto.getName())) {
            throw new CategoryNameAlreadyExistException("Category name :" + categoryCreateRequestDto.getName() + " already exist");
        }
        CategoryCreateResponseDto savedCategory = categoryService.create(categoryCreateRequestDto);
        return new ResponseEntity(savedCategory, HttpStatus.CREATED);
    }

    @Operation(summary = "List all categories")
    @GetMapping("list")
    public List<CategoryFindResponseDto> findAll() {
        return categoryService.findAll();
    }

    @Operation(summary = "List category by id")
    @GetMapping("{id}")
    public CategoryFindResponseDto findById(@PathVariable final UUID id) {
        return categoryService.findById(id);
    }

    @Operation(summary = "Update category by id")
    @PutMapping("update/{id}")
    public CategoryUpdateResponseDto update(@PathVariable UUID id, @RequestBody CategoryUpdateRequestDto categoryUpdateRequestDto) {
        return categoryService.update(id, categoryUpdateRequestDto);

    }

    @Operation(summary = "delete category by id")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable final UUID id) {
        categoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @Operation(summary = "Sort and Filter for Category list page")
    @GetMapping("findByPage/{pageNumber}")
    public List<CategoryFindResponseDto> findByPage(@PathVariable("pageNumber") int pageNumber,
                                                       @RequestParam("sortField") String sortField,
                                                       @RequestParam("sortDir") String sortDir,
                                                       @RequestParam("keyword") String keyword) {

        return categoryService.findByPage(pageNumber, sortField, sortDir, keyword);

    }
    @Operation(summary = "Upload Image of Category ")
    @PostMapping(value = "/upload-image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> uploadImage(@RequestParam @NotNull final UUID id,
                                                  @NotNull @RequestParam(name = "fileImage", value = "fileImage") final MultipartFile multipartFile)
            throws IOException, SubCategoryNotFoundException {
        if (!categoryService.isCategoryExistById(id)) {
            throw new CategoryNotFoundException("Category does not exist  with id " + id);
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uploadDir = "spring6-ecommerce-category/category-images";
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());

            categoryService.updateFileNameById(id, fileName);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
