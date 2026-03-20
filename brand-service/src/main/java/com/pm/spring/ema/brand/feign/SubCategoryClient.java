package com.pm.spring.ema.brand.feign;

import com.pm.spring.ema.common.util.config.CustomFeignConfiguration;
import com.pm.spring.ema.common.util.dto.SubCategoryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "spring6-subcategory", url = "localhost:1006/api/sub-category", configuration = CustomFeignConfiguration.class)
public interface SubCategoryClient {

    @GetMapping("/{subCategoryId}")
    SubCategoryDto getById(@RequestHeader("Authorization") String token, @PathVariable UUID subCategoryId);
}
