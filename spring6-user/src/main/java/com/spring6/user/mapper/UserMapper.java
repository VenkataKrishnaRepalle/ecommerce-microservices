package com.spring6.user.mapper;

import com.spring6.user.dto.*;
import com.spring6.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface UserMapper {

    @Mapping(target = "imageUrl", expression = "java(getImageUrl(requestUrl, user.getId()))")
    UserFindResponseDto userToUserFindResponseDto(User user, String requestUrl);

    UserFindResponseDto userToUserFindResponseDto(User user);

    User userCreateRequestDtoToUser(UserCreateRequestDto userCreateRequestDto);

    UserCreateResponseDto userToUserCreateResponseDto(User user);

    User userUpdateRequestDtoToUser(UserUpdateRequestDto userUpdateRequestDto);

    UserUpdateResponseDto userToUserUpdateResponseDto(User user);

    default String getImageUrl(String requestUrl, UUID userId) {
        return requestUrl + "/api/user/" + userId + "/image";
    }
}
