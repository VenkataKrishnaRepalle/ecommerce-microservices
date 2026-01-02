package com.pm.spring.ema.mailservice.feign;

import com.pm.spring.ema.common.util.dto.CustomerDto;
import com.pm.spring.ema.common.util.exception.CustomErrorDecoder;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "customer-service", url = "localhost:1003/customer", configuration = CustomErrorDecoder.class)
public interface UserServiceFeignClient {
    @GetMapping("/get/{userId}")
    CustomerDto getById(@PathVariable @NotNull UUID userId);
}
