package com.pm.spring.ema.mailservice.feign;

import com.pm.spring.ema.mailservice.dto.UserResponseDto;
import com.pm.spring.ema.mailservice.exception.CustomErrorDecoder;
import jakarta.validation.constraints.NotNull;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "authentication-service", url = "localhost:1001/", configuration = CustomErrorDecoder.class)
public interface UserServiceFeignClient {
    @GetMapping("get-by-userId/{userId}")
    UserResponseDto getById(@PathVariable @NotNull UUID userId);
}
