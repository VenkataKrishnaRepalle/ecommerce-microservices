package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.common.dto.CategoryFindResponseDto;
import com.spring6.ecommerce.common.utils.FileUploadUtils;
import com.spring6.ecommerce.dto.CategoryCreateRequestDto;
import com.spring6.ecommerce.dto.CategoryCreateResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
import com.spring6.ecommerce.exception.CategoryNameAlreadyExistException;
import com.spring6.ecommerce.exception.CategoryNotFoundException;
import com.spring6.ecommerce.service.CategoryService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
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
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("addCategory")
    public ResponseEntity<HttpStatus> createCategory(@RequestBody @Valid final CategoryCreateRequestDto categoryCreateRequestDto) throws CategoryNameAlreadyExistException {
        if (categoryService.isNameExist(categoryCreateRequestDto.getName())) {
            throw new CategoryNameAlreadyExistException("Category name :" + categoryCreateRequestDto.getName() + " already exist");
        }
        CategoryCreateResponseDto saveCategory = categoryService.createCategories(categoryCreateRequestDto);
        return new ResponseEntity(saveCategory, HttpStatus.CREATED);
    }

    @GetMapping("listCategory")
    public List<CategoryFindResponseDto> listAllCategories() {
        return categoryService.listAll();
    }
    @GetMapping("{categoryId}")
    public CategoryFindResponseDto getCategoryById(@PathVariable final UUID categoryId){
        return categoryService.findCategoryById(categoryId);
    }
    @PutMapping("update/{id}")
    public  CategoryUpdateResponseDto updateCategory(@PathVariable UUID id, @RequestBody CategoryUpdateRequestDto categoryDto){
        return categoryService.updateCategory(id, categoryDto);
    }
    @DeleteMapping("delete/{categoryId}")
    public ResponseEntity<HttpStatus>  deleteById(@PathVariable final UUID categoryId) {
        categoryService.deleteCategoryById(categoryId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("findByPage/{pageNumber}")
    public List<CategoryFindResponseDto> findByPage(@PathVariable("pageNumber") int pageNumber,
                                                   @RequestParam("sortField") String sortField,
                                                   @RequestParam("sortDir") String sortDir,
                                                   @RequestParam("keyword") String keyword) {

        return categoryService.findByPage(pageNumber, sortField, sortDir, keyword);

    }

    @PostMapping(value = "/upload-category-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> addProductImage(@RequestParam @NotNull final UUID categoryId,
                                                      @NotNull @RequestParam(name = "fileImage", value = "fileImage") final MultipartFile multipartFile)
            throws IOException, CategoryNotFoundException {
        if (!categoryService.isCategoryExist(categoryId)) {
            throw new CategoryNotFoundException("Category does not exist  with id " + categoryId);
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            String uploadDir = "spring6-ecommerce-category/category-images";
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());

            categoryService.updateImageFile(categoryId, fileName);
        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

}
