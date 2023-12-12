package com.pm.spring.ema.auth.dto.mapper;

import com.pm.spring.ema.auth.dto.response.RoleResponseDto;
import com.pm.spring.ema.auth.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleResponseDto roleToRoleResponseDto(Role role);
}
