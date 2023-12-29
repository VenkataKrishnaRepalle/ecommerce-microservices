//package com.pm.spring.ema.order.feign;
//
//import com.pm.spring.ema.common.dto.CategoryFindResponseDto;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.List;
//
//@FeignClient(name = "spring6-category", url = "localhost:1000")
//public interface CategoryServiceFeignClient {
//
//    @GetMapping("category/list")
//    List<CategoryFindResponseDto> listAll();
//}