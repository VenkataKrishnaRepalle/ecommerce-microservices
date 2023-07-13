package com.spring6.auth.dto.mapper;

import com.spring6.auth.dto.request.AccountCreateRequestDto;
import com.spring6.auth.dto.response.UserAuditResponseDto;
import com.spring6.auth.model.entity.Account;
import com.spring6.common.dto.UserInfoResponseDto;
import org.mapstruct.Mapper;


@Mapper
public interface AccountMapper {

    Account userCreateRequestDtoToUser(AccountCreateRequestDto accountCreateRequestDto);
    UserInfoResponseDto userToUserProfileResponseDto(Account account);
    UserAuditResponseDto userToUserAuditResponseDto(Account account);
}
