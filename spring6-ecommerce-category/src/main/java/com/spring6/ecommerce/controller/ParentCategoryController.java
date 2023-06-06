package com.spring6.ecommerce.controller;
import com.spring6.ecommerce.common.dto.ParentCategoryFindResponseDto;
import com.spring6.ecommerce.dto.CategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.CategoryUpdateResponseDto;
import com.spring6.ecommerce.dto.ParentCategoryUpdateRequestDto;
import com.spring6.ecommerce.dto.ParentCategoryUpdateResponseDto;
import com.spring6.ecommerce.service.ParentCategoryService;
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
