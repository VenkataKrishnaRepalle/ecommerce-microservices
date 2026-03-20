package com.pm.spring.ema.product.feign;

import java.util.List;
import java.util.UUID;

import com.pm.spring.ema.common.util.config.CustomFeignConfiguration;
import com.pm.spring.ema.common.util.dto.BrandDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "spring6-ecommerce-brand", url = "localhost:1007", configuration = CustomFeignConfiguration.class)
public interface BrandClient {

  @GetMapping("brand/list")
  List<BrandDto> listAll();

  @GetMapping("brand/{brandId}")
  BrandDto getById(@RequestHeader("Authorization") String token, @PathVariable UUID brandId);
}
