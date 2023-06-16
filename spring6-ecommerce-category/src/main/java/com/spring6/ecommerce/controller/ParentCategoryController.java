package com.spring6.ecommerce.controller;
import com.spring6.ecommerce.common.dto.ParentCategoryFindResponseDto;
import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.exception.ParentCategoryNameAlreadyExistException;
import com.spring6.ecommerce.service.ParentCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("parent-category")
public class ParentCategoryController {
    private final ParentCategoryService parentCategoryService;

    @PostMapping("addParentCategory")
    public ResponseEntity<HttpStatus> createParentCategory(@RequestBody @Valid final ParentCategoryCreateRequestDto parentCategoryCreateRequestDto) throws ParentCategoryNameAlreadyExistException {
        if (parentCategoryService.isNameExist(parentCategoryCreateRequestDto.getName())) {
            throw new ParentCategoryNameAlreadyExistException("Parent Category name :" + parentCategoryCreateRequestDto.getName() + " already exist");
        }
        ParentCategoryCreateResponseDto saveParentCategory = parentCategoryService.createParentCategories(parentCategoryCreateRequestDto);
        return new ResponseEntity(saveParentCategory, HttpStatus.CREATED);
    }

    @GetMapping("getList")
    public List<ParentCategoryFindResponseDto> listAll() {
        return parentCategoryService.listAll();
    }

    @GetMapping("{parentCategoryId}")
    public ParentCategoryFindResponseDto getParentCategoryById(@PathVariable final UUID parentCategoryId){
        return parentCategoryService.findCategoryById(parentCategoryId);
    }
    @PutMapping("update/{id}")
    public ParentCategoryUpdateResponseDto updateParentCategory(@PathVariable UUID id, @RequestBody ParentCategoryUpdateRequestDto parentCategoryUpdateRequestDto){
        return parentCategoryService.updateParentCategory(id, parentCategoryUpdateRequestDto);

    }
    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteById(@PathVariable final UUID id) {
        parentCategoryService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
