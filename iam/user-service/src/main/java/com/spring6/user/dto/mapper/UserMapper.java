package com.pm.spring.ema.user.dto.mapper;

import com.pm.spring.ema.user.dto.request.UserCreateRequestDto;
import com.pm.spring.ema.user.dto.request.UserUpdateRequestDto;
import com.pm.spring.ema.user.dto.response.UserCreateResponseDto;
import com.pm.spring.ema.user.dto.response.UserFindResponseDto;
import com.pm.spring.ema.user.dto.response.UserUpdateResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

@Mapper
public interface UserMapper {

    @Mapping(target = "imageUrl", expression = "java(getImageUrl(requestUrl, user.getId()))")
    UserFindResponseDto userToUserFindResponseDto(com.pm.spring.ema.user.model.entity.User user, String requestUrl);

    UserFindResponseDto userToUserFindResponseDto(com.pm.spring.ema.user.model.entity.User user);

    com.pm.spring.ema.user.model.entity.User userCreateRequestDtoToUser(UserCreateRequestDto userCreateRequestDto);

    UserCreateResponseDto userToUserCreateResponseDto(com.pm.spring.ema.user.model.entity.User user);

    com.pm.spring.ema.user.model.entity.User userUpdateRequestDtoToUser(UserUpdateRequestDto userUpdateRequestDto);

    UserUpdateResponseDto userToUserUpdateResponseDto(com.pm.spring.ema.user.model.entity.User user);

    default String getImageUrl(String requestUrl, UUID userId) {
        return requestUrl + "/api/user/" + userId + "/image";
    }
}
