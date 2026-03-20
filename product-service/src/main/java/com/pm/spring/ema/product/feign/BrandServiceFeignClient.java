package com.pm.spring.ema.product.feign;

import com.pm.spring.ema.common.util.dto.BrandFindResponseDto;
import java.util.List;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "spring6-ecommerce-brand", url = "localhost:1007")
public interface BrandServiceFeignClient {

  @GetMapping("brand/list")
  List<BrandFindResponseDto> listAll();

  @GetMapping("brand/{brandId}")
  BrandFindResponseDto getById(@PathVariable UUID brandId);
}
