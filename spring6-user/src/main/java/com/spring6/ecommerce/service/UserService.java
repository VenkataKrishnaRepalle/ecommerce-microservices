package com.spring6.ecommerce.service;

import com.spring6.ecommerce.exception.UserNotFoundException;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserFindResponseDto> findAll();

    List<UserFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword);

    UserFindResponseDto findById(UUID id);

    Boolean isUsernameExist(String username);

    UserCreateResponseDto create(UserCreateRequestDto userCreateRequestDto);

    UserUpdateResponseDto update(UUID id, UserUpdateRequestDto userCreateRequestDto) throws UserNotFoundException;

    void deleteById(UUID id) throws UserNotFoundException;
}
