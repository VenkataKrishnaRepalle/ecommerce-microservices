package com.spring6.user.service;

import com.spring6.common.dto.UserFindResponseDto;
import com.spring6.user.dto.*;
import com.spring6.user.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserFindResponseDto> findAll();

    List<UserFindResponseDto> findByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDir, UserSearchKeywordEnum searchField, String searchKeyword);

    UserFindResponseDto findById(UUID id) throws UserNotFoundException;

    UserCreateResponseDto create(UserCreateRequestDto userCreateRequestDto);

    UserUpdateResponseDto update(UUID id, UserUpdateRequestDto userCreateRequestDto) throws UserNotFoundException;

    void deleteById(UUID id) throws UserNotFoundException;

    Boolean isNameExist(String name);

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID userId, String fileName);
}
