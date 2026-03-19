package com.pm.spring.ema.mailservice.feign;

import com.pm.spring.ema.common.util.config.CustomFeignConfiguration;
import com.pm.spring.ema.common.util.dto.ApiResponse;
import com.pm.spring.ema.common.util.dto.CustomerDetailsDto;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "customer-service",
    url = "localhost:1003/customer",
    configuration = CustomFeignConfiguration.class)
public interface UserServiceFeignClient {
  @GetMapping("/get/{userId}")
  ApiResponse<CustomerDetailsDto> getById(@PathVariable @NotNull UUID userId);
}
