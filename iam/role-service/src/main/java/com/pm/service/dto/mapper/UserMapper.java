package com.pm.service.dto.mapper;

import com.pm.service.dto.request.UserCreateRequestDto;
import com.pm.service.dto.request.UserUpdateRequestDto;
import com.pm.service.dto.response.UserCreateResponseDto;
import com.pm.service.dto.response.UserFindResponseDto;
import com.pm.service.dto.response.UserUpdateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface UserMapper {

    @Mapping(target = "imageUrl", expression = "java(getImageUrl(requestUrl, user.getId()))")
    UserFindResponseDto userToUserFindResponseDto(UserProfile userProfile, String requestUrl);

    UserFindResponseDto userToUserFindResponseDto(UserProfile userProfile);

    UserProfile userCreateRequestDtoToUser(UserCreateRequestDto userCreateRequestDto);

    UserCreateResponseDto userToUserCreateResponseDto(UserProfile userProfile);

    UserProfile userUpdateRequestDtoToUser(UserUpdateRequestDto userUpdateRequestDto);

    UserUpdateResponseDto userToUserUpdateResponseDto(UserProfile userProfile);

    default String getImageUrl(String requestUrl, UUID userId) {
        return requestUrl + "/api/user/" + userId + "/image";
    }
}
