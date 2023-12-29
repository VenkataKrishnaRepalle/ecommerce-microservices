package com.pm.service.service;

import com.pm.service.dto.response.UserUpdateResponseDto;
import com.pm.service.exception.UserNotFoundException;
import com.pm.service.model.enums.UserStatus;
import com.pm.service.dto.request.UserUpdateRequestDto;
import com.pm.service.dto.response.UserFindResponseDto;
import com.pm.service.dto.enums.SortOrderDirectionEnum;
import com.pm.service.dto.enums.UserSearchKeywordEnum;
import com.pm.service.dto.enums.UserSortFieldEnum;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserFindResponseDto> getAll();

    List<UserFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, UserSortFieldEnum sortField, SortOrderDirectionEnum sortDir, UserSearchKeywordEnum searchField, String searchKeyword);

    UserFindResponseDto getById(UUID id) throws UserNotFoundException;

    String getPhotoById(UUID id) throws UserNotFoundException;

    UserUpdateResponseDto update(UUID id, UserUpdateRequestDto userCreateRequestDto) throws UserNotFoundException;

    void deleteById(UUID id) throws UserNotFoundException;

    Boolean isUsernameExist(String name);

    Boolean isIdExist(UUID uuid);

    String updateImageName(UUID userId, String fileName);

    Boolean isEmailExist(String email);

    Boolean updateUserStatus(UUID id, UserStatus userStatus);
}
