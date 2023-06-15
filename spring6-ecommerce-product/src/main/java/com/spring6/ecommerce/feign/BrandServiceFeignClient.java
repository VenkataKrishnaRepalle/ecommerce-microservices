package com.spring6.ecommerce.feign;

import com.spring6.ecommerce.common.dto.BrandFindResponesDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "spring6-ecommerce-brand", url = "localhost:1001")
public interface BrandServiceFeignClient {

    @GetMapping("brand/list")
    List<BrandFindResponesDto> listAll();

    @GetMapping("brand/{brandId}")
    BrandFindResponesDto getById(@PathVariable UUID brandId);

}