package com.pm.spring.ema.auth.dto.mapper;

import com.pm.spring.ema.auth.dto.response.PermissionResponseDto;
import com.pm.spring.ema.auth.model.entity.Privilege;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapper {
    PermissionResponseDto permissionToPermissionResponseDto(Privilege privilege);
}
