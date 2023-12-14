package com.pm.spring.ema.authentication.common.dto.mapper;

import com.pm.spring.ema.authentication.common.dto.response.PermissionResponseDto;
import com.pm.spring.ema.authentication.common.model.entity.Privilege;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapper {
    PermissionResponseDto permissionToPermissionResponseDto(Privilege privilege);
}
