package com.spring6.user.service;

import com.spring6.user.dto.*;
import com.spring6.user.enums.UserSearchKeywordEnum;
import com.spring6.user.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserFindResponseDto> getAllUsers();

    List<UserFindResponseDto> getUsersByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, UserSearchKeywordEnum searchField, String searchKeyword);

    UserFindResponseDto getUserById(UUID id) throws UserNotFoundException;

    UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto);

    UserUpdateResponseDto updateUser(UUID id, UserUpdateRequestDto userCreateRequestDto) throws UserNotFoundException;

    void deleteUserById(UUID id) throws UserNotFoundException;

    Boolean isUserNameExist(String name);

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID userId, String fileName);
}
