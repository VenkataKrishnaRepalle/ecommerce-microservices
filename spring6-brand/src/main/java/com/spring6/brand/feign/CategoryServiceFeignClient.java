package com.spring6.brand.feign;

import com.spring6.common.dto.CategoryFindResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "spring6-category", url = "localhost:1000")
public interface CategoryServiceFeignClient {

    @GetMapping("category/list")
    List<CategoryFindResponseDto> listAll();
}