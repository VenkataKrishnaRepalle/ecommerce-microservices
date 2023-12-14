package com.pm.spring.ema.auth.dto.mapper;

import com.pm.spring.ema.auth.dto.request.AccountCreateRequestDto;
import com.pm.spring.ema.auth.dto.response.UserAuditResponseDto;
import com.pm.spring.ema.auth.dto.response.UserInfoResponseDto;
import com.pm.spring.ema.auth.model.entity.Account;
import org.mapstruct.Mapper;


@Mapper
public interface AccountMapper {

    Account userCreateRequestDtoToUser(AccountCreateRequestDto accountCreateRequestDto);
    UserInfoResponseDto userToUserProfileResponseDto(Account account);
    UserAuditResponseDto userToUserAuditResponseDto(Account account);
}
