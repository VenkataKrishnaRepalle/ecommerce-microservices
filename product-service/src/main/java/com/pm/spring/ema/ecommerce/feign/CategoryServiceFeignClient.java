package com.pm.spring.ema.ecommerce.feign;

import com.pm.spring.ema.category.common.dto.categoryDto.response.CategoryFindResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "spring6-ecommerce-category", url = "localhost:1000")
public interface CategoryServiceFeignClient {

    @GetMapping("category/list")
    List<CategoryFindResponseDto> listAll();
}