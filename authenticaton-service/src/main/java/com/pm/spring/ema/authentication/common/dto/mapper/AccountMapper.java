package com.pm.spring.ema.authentication.common.dto.mapper;

import com.pm.spring.ema.authentication.common.dto.request.AccountCreateRequestDto;
import com.pm.spring.ema.authentication.common.dto.response.UserAuditResponseDto;
import com.pm.spring.ema.authentication.common.dto.response.UserInfoResponseDto;
import com.pm.spring.ema.authentication.common.model.entity.Account;
import org.mapstruct.Mapper;


@Mapper
public interface AccountMapper {

    Account userCreateRequestDtoToUser(AccountCreateRequestDto accountCreateRequestDto);
    UserInfoResponseDto userToUserProfileResponseDto(Account account);
    UserAuditResponseDto userToUserAuditResponseDto(Account account);
}
