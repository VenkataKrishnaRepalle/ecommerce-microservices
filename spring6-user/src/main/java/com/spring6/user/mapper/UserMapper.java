package com.spring6.user.mapper;

import com.spring6.user.dto.*;
import com.spring6.user.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserFindResponseDto userToUserFindResponseDto(User user);

    User userCreateRequestDtoToUser(UserCreateRequestDto userCreateRequestDto);

    UserCreateResponseDto userToUserCreateResponseDto(User user);

    User userUpdateRequestDtoToUser(UserUpdateRequestDto userUpdateRequestDto);

    UserUpdateResponseDto userToUserUpdateResponseDto(User user);
}
