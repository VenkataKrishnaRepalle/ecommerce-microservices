package com.pm.spring.ema.feign;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "spring6-ecommerce-category", url = "localhost:1000")
public interface CategoryServiceFeignClient {

  //    @GetMapping("category/list")
  //    List<CategoryFindResponseDto> listAll();
}
