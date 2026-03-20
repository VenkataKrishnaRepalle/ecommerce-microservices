package com.pm.spring.ema.brand.feign;

import com.pm.spring.ema.common.util.config.CustomFeignConfiguration;
import com.pm.spring.ema.common.util.dto.CategoryDto;

import java.util.List;
import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "spring6-category", url = "localhost:1006/api/category", configuration = CustomFeignConfiguration.class)
public interface CategoryClient {

    @GetMapping("/list")
    List<CategoryDto> listAll(@RequestHeader("Authorization") String token);

    @GetMapping("/{categoryId}")
    CategoryDto getById(@RequestHeader("Authorization") String token, @PathVariable UUID categoryId);
}
