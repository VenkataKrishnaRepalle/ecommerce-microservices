package com.spring6.ecommerce.feign;

import com.spring6.ecommerce.commonutil.dto.CategoryFindResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "spring6-ecommerce-category", url = "localhost:1000")
public interface CategoryServiceFeignClient {

    @GetMapping("category/list")
    List<CategoryFindResponseDto> listAll();
}