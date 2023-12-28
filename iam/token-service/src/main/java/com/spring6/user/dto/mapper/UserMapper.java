package com.pm.spring.ema.user.dto.mapper;

import com.pm.spring.ema.user.dto.request.UserCreateRequestDto;
import com.pm.spring.ema.user.dto.request.UserUpdateRequestDto;
import com.pm.spring.ema.user.dto.response.UserCreateResponseDto;
import com.pm.spring.ema.user.dto.response.UserFindResponseDto;
import com.pm.spring.ema.user.dto.response.UserUpdateResponseDto;
import com.pm.spring.ema.user.model.entity.UserProfile;
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
