package com.pm.spring.ema.authentication.common.dto.mapper;

import com.pm.spring.ema.authentication.common.dto.response.RoleResponseDto;
import com.pm.spring.ema.authentication.common.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleResponseDto roleToRoleResponseDto(Role role);
}
