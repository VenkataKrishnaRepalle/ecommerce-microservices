package com.pm.spring.ema.brand.feign;

import com.pm.spring.ema.auth.dto.response.UserInfoResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "spring6-auth", url = "localhost:1000")
public interface AuthServiceFeignClient {

    @GetMapping("api/v1/auth/user-info")
    UserInfoResponseDto getUserInfo();
}