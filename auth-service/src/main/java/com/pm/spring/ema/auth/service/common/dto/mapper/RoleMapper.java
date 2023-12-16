package com.pm.spring.ema.auth.service.common.dto.mapper;

import com.pm.spring.ema.auth.service.common.dto.response.RoleResponseDto;
import com.pm.spring.ema.auth.service.common.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleResponseDto roleToRoleResponseDto(Role role);
}
