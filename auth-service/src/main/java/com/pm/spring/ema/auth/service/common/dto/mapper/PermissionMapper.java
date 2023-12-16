package com.pm.spring.ema.auth.service.common.dto.mapper;

import com.pm.spring.ema.auth.service.common.dto.response.PermissionResponseDto;
import com.pm.spring.ema.auth.service.common.model.entity.Privilege;
import org.mapstruct.Mapper;

@Mapper
public interface PermissionMapper {
    PermissionResponseDto permissionToPermissionResponseDto(Privilege privilege);
}
