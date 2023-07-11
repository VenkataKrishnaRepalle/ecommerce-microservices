package com.spring6.auth.dto.mapper;

import com.spring6.auth.dto.response.PermissionResponseDto;
import com.spring6.auth.model.entity.Permission;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapper {
    PermissionResponseDto permissionToPermissionResponseDto(Permission permission);
}
