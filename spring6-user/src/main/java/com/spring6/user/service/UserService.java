package com.spring6.user.service;

import com.spring6.user.dto.*;
import com.spring6.user.entity.UserStatus;
import com.spring6.user.enums.SortOrderDirectionEnum;
import com.spring6.user.enums.UserSearchKeywordEnum;
import com.spring6.user.enums.UserSortFieldEnum;
import com.spring6.user.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserFindResponseDto> getAll();

    List<UserFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, UserSortFieldEnum sortField, SortOrderDirectionEnum sortDir, UserSearchKeywordEnum searchField, String searchKeyword);

    UserFindResponseDto getById(UUID id) throws UserNotFoundException;

    String getPhotoById(UUID id) throws UserNotFoundException;

    UserCreateResponseDto create(UserCreateRequestDto userCreateRequestDto);

    UserUpdateResponseDto update(UUID id, UserUpdateRequestDto userCreateRequestDto) throws UserNotFoundException;

    void deleteById(UUID id) throws UserNotFoundException;

    Boolean isUsernameExist(String name);

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID userId, String fileName);

    Boolean isEmailExist(String email);

    Boolean updateUserStatus(UUID id, UserStatus userStatus);
}
