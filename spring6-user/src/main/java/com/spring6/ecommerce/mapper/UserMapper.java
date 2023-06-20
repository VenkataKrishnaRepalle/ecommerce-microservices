package com.spring6.ecommerce.mapper;

import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.entity.User;
import org.mapstruct.Mapper;

@Mapper
public interface UserMapper {

    UserFindResponseDto userToUserFindResponseDto(User user);

    User userCreateRequestDtoToUser(UserCreateRequestDto userCreateRequestDto);

    UserCreateResponseDto userToUserCreateResponseDto(User user);

    User userUpdateRequestDtoToUser(UserUpdateRequestDto userUpdateRequestDto);

    UserUpdateResponseDto userToUserUpdateResponseDto(User user);
}
