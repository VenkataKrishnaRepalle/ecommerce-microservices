package com.pm.spring.ema.authservice.dao;

import com.pm.spring.ema.authservice.dto.CustomerResponseDto;
import org.apache.ibatis.annotations.Param;

public interface AuthDao {
  CustomerResponseDto login(@Param("email") String email);
}
