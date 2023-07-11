package com.spring6.auth.dto.mapper;

import com.spring6.auth.dto.response.RoleResponseDto;
import com.spring6.auth.model.entity.Role;
import org.mapstruct.Mapper;

@Mapper
public interface RoleMapper {
    RoleResponseDto roleToRoleResponseDto(Role role);
}
