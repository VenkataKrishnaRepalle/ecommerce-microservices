package com.spring6.auth.mapper;

import com.spring6.auth.dto.UserCreateRequestDto;
import com.spring6.common.dto.UserInfoResponseDto;
import com.spring6.auth.entity.User;
import org.mapstruct.Mapper;


@Mapper
public interface UserMapper {

    User userCreateRequestDtoToUser(UserCreateRequestDto userCreateRequestDto);
    UserInfoResponseDto userToUserProfileResponseDto(User user);
}
