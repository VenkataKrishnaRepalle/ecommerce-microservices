package com.pm.spring.ema.user.service;

import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.util.exception.ErrorMessage;
import com.pm.spring.ema.user.dto.request.UserUpdateRequestDto;
import com.pm.spring.ema.user.dto.response.UserFindResponseDto;
import com.pm.spring.ema.user.dto.response.UserUpdateResponseDto;
import com.pm.spring.ema.user.model.entity.UserProfile;
import com.pm.spring.ema.user.dto.enums.SortOrderDirectionEnum;
import com.pm.spring.ema.user.dto.enums.UserSearchKeywordEnum;
import com.pm.spring.ema.user.dto.enums.UserSortFieldEnum;
import com.pm.spring.ema.user.model.enums.UserStatus;
import com.pm.spring.ema.user.exception.UserEmailAlreadyExistException;
import com.pm.spring.ema.user.exception.UserNameAlreadyExistException;
import com.pm.spring.ema.user.exception.UserNotFoundException;
import com.pm.spring.ema.user.exception.UserPhotoNotFoundException;
import com.pm.spring.ema.user.dto.mapper.UserMapper;
import com.pm.spring.ema.user.model.repository.UserRepository;
import com.pm.spring.ema.user.filter.TraceIdHolder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final HttpServletRequest request;

    public List<UserFindResponseDto> getAll() {
        log.info("UserService:getAllUsers execution started.");
        log.debug("UserService:getAllUsers traceId: {}", TraceIdHolder.getTraceId());


        List<UserFindResponseDto> userFindResponseDtoList = userRepository.findAll()
                .stream()
                .map(user -> {
                    if (user.getPhoto() != null) {
                        return userMapper.userToUserFindResponseDto(user, getServerURL());
                    } else {
                        return userMapper.userToUserFindResponseDto(user);
                    }
                })
                .toList();

        log.debug("UserService:findAll traceId: {}, response {} ", TraceIdHolder.getTraceId(), userFindResponseDtoList);
        log.info("UserService:findAll execution ended.");

        return userFindResponseDtoList;
    }

    public List<UserFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, UserSortFieldEnum sortField, SortOrderDirectionEnum sortOrderDirectionEnum, UserSearchKeywordEnum searchField, String searchKeyword) {
        log.info("UserService:getUsersByPage execution started.");
        log.debug("UserService:getUsersByPage traceId: {},  pageNumber: {}, perPageCount: {}, sortField: {}, sortDirectory: {}, searchField: {}, searchKeyword: {}", TraceIdHolder.getTraceId(), pageNumber, perPageCount, sortField, sortOrderDirectionEnum, searchField, searchKeyword);

        Pageable pageable;

        if (sortField == null) {
            pageable = PageRequest.of(pageNumber - 1, perPageCount);
        } else {
            String fieldName = getUserSortFieldName(sortField);
            Sort sort = Sort.by(fieldName);
            sort = sortOrderDirectionEnum.equals(SortOrderDirectionEnum.ASCENDING_ORDER) ? sort.ascending() : sort.descending();
            pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);
        }

        Page<UserProfile> userList;

        if (searchKeyword != null && searchField.equals(UserSearchKeywordEnum.FIRST_NAME)) {
            userList = userRepository.findAllByFirstName(searchKeyword, pageable);

        } else if (searchKeyword != null && searchField.equals(UserSearchKeywordEnum.LAST_NAME)) {
            userList = userRepository.findAllByLastName(searchKeyword, pageable);

        } else if (searchKeyword != null && searchField.equals(UserSearchKeywordEnum.EMAIL)) {
            userList = userRepository.findAllByEmail(searchKeyword, pageable);

        } else if (searchKeyword != null && searchField.equals(UserSearchKeywordEnum.USERNAME)) {
            userList = userRepository.findAllByUsername(searchKeyword, pageable);
        } else {
            userList = userRepository.findAll(pageable);
        }

        List<UserFindResponseDto> userFindResponseDtoList = userList.stream()
                .map(user -> {
                    if (user.getPhoto() != null) {
                        return userMapper.userToUserFindResponseDto(user, getServerURL());
                    } else {
                        return userMapper.userToUserFindResponseDto(user);
                    }
                })
                .toList();

        log.debug("UserService:getUsersByPage traceId: {}", TraceIdHolder.getTraceId());
        log.info("UserService:getUsersByPage execution ended.");

        return userFindResponseDtoList;

    }

    @Override
    public UserFindResponseDto getById(UUID id) throws UserNotFoundException {
        log.info("UserService:getUserById execution started.");
        log.debug("UserService:getUserById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<UserProfile> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            log.error("UserService:getUserById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4501, id.toString()));
            log.info("UserService:getUserById execution ended.");
            throw new UserNotFoundException(ErrorCodes.E4501, id.toString());
        }
        String serverUrl = request.getRequestURL().toString() + request.getServerPort();

        UserFindResponseDto userFindResponseDto = userMapper.userToUserFindResponseDto(optionalUser.get(), serverUrl);

        log.debug("UserService:getUserById traceId: {}, response: {}", TraceIdHolder.getTraceId(), userFindResponseDto);
        log.info("UserService:getUserById execution ended.");

        return userFindResponseDto;
    }

    public String getPhotoById(UUID id) throws UserNotFoundException {
        log.info("UserService:getPhotoById execution started.");
        log.debug("UserService:getPhotoById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<UserProfile> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            log.error("UserService:getPhotoById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4513, id.toString()));
            log.info("UserService:getPhotoById execution ended.");
            throw new UserNotFoundException(ErrorCodes.E4513, id.toString());
        }

        UserProfile userProfile = optionalUser.get();

        if (userProfile.getPhoto() == null) {
            log.error("UserService:getPhotoById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4514, id.toString()));
            log.info("UserService:getPhotoById execution ended.");
            throw new UserPhotoNotFoundException(ErrorCodes.E4514, id.toString());
        }

        log.debug("UserService:getPhotoById traceId: {}, photo : {}", TraceIdHolder.getTraceId(), userProfile.getPhoto());
        log.info("UserService:getPhotoById execution ended.");
        return userProfile.getPhoto();
    }

    @Override
    public UserUpdateResponseDto update(final UUID id, UserUpdateRequestDto userUpdateRequestDto)
            throws UserNotFoundException {
        log.info("UserService:updateUser execution started.");
        log.debug("UserService:updateUser traceId: {}, id: {}, userCreateRequestDto: {}", TraceIdHolder.getTraceId(), id, userUpdateRequestDto);

        Optional<UserProfile> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            log.error("UserService:updateUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4502, userUpdateRequestDto.getUsername()));
            throw new UserNotFoundException(ErrorCodes.E4502, id.toString());
        }

        if (isUsernameExist(userUpdateRequestDto.getUsername())) {
            log.error("UserService:createUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4511, userUpdateRequestDto.getUsername()));
            throw new UserNameAlreadyExistException(ErrorCodes.E4511, userUpdateRequestDto.getUsername());
        }

        if (isEmailExist(userUpdateRequestDto.getEmail())) {
            log.error("UserService:updateUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4510, userUpdateRequestDto.getEmail()));
            throw new UserEmailAlreadyExistException(ErrorCodes.E4510, userUpdateRequestDto.getEmail());
        }

        UserProfile userProfile = userMapper.userUpdateRequestDtoToUser(userUpdateRequestDto);
        userProfile.setId(optionalUser.get().getId());

        UserProfile userProfileUpdated = userRepository.save(userProfile);
        UserUpdateResponseDto userUpdateResponseDto = userMapper.userToUserUpdateResponseDto(userProfileUpdated);

        log.debug("UserService:updateUser traceId: {}, response: {}", TraceIdHolder.getTraceId(), userUpdateResponseDto);
        log.info("UserService:updateUser execution ended.");

        return userUpdateResponseDto;
    }

    @Override
    public void deleteById(UUID id) throws UserNotFoundException {
        log.info("UserService:deleteUserById execution started.");
        log.debug("UserService:deleteUserById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Long userCountById = userRepository.countById(id);
        if (userCountById == 0) {
            log.error("UserService:deleteUserById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4503, id.toString()));
            throw new UserNotFoundException(ErrorCodes.E4503, id.toString());
        }

        userRepository.deleteById(id);
        log.info("UserService:deleteUserById execution ended.");

    }

    @Override
    public String updateImageName(UUID userId, String fileName) {
        log.info("UserService:updateImageName execution started.");
        log.debug("UserService:updateImageName traceId: {}, userId:{}, fileName: {}", TraceIdHolder.getTraceId(), userId, fileName);

        Optional<UserProfile> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            log.error("UserService:updateImageName traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4504, userId.toString()));
            throw new UserNotFoundException(ErrorCodes.E4504, userId.toString());
        }

        UserProfile userProfile = optionalUser.get();
        userProfile.setPhoto(fileName);

        UserProfile userProfileUpdated = userRepository.save(userProfile);

        log.debug("UserService:updateImageName traceId: {}, updatedImageName: {}", TraceIdHolder.getTraceId(), userProfileUpdated.getPhoto());
        log.info("UserService:updateImageName execution ended.");

        return userProfileUpdated.getPhoto();
    }

    @Override
    public Boolean isEmailExist(String email) {
        log.info("UserService:isUserEmailExist execution started.");
        log.debug("UserService:isUserEmailExist traceId: {}, username: {}", TraceIdHolder.getTraceId(), email);

        Optional<UserProfile> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

    @Override
    public Boolean updateUserStatus(UUID id, UserStatus userStatus) {

        log.info("UserService:updateUserStatus execution started.");
        log.debug("UserService:updateUserStatus traceId: {}, id:{}, userStatus: {}", TraceIdHolder.getTraceId(), id, userStatus);

        Optional<UserProfile> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            log.error("UserService:updateUserStatus traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4512, id.toString()));
            throw new UserNotFoundException(ErrorCodes.E4512, id.toString());
        }

        UserProfile userProfile = optionalUser.get();
        userProfile.setStatus(userStatus);

        UserProfile userProfileUpdated = userRepository.save(userProfile);

        if (userProfileUpdated == null) {
            return Boolean.FALSE;
        }

        log.debug("UserService:updateUserStatus traceId: {}, userStatus: {}", TraceIdHolder.getTraceId(), userProfileUpdated.getStatus());
        log.info("UserService:updateUserStatus execution ended.");

        return Boolean.TRUE;

    }

    @Override
    public Boolean isUsernameExist(String username) {
        log.info("UserService:isUserNameExist execution started.");
        log.debug("UserService:isUserNameExist traceId: {}, username: {}", TraceIdHolder.getTraceId(), username);

        Optional<UserProfile> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isIdExist(UUID uuid) {
        log.info("UserService:isIdExist execution started.");
        log.debug("UserService:isIdExist traceId: {}, id: {}", TraceIdHolder.getTraceId(), uuid);

        Optional<UserProfile> optionalUser = userRepository.findById(uuid);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private String getServerURL() {
        return request.getServerName() + ":" + request.getServerPort();
    }

    private String getUserSortFieldName(UserSortFieldEnum sortField) {
        String fieldName = "";

        if (sortField == UserSortFieldEnum.FIRST_NAME) {
            fieldName = "firstName";
        } else if (sortField == UserSortFieldEnum.LAST_NAME) {
            fieldName = "lastName";
        } else if (sortField == UserSortFieldEnum.EMAIL) {
            fieldName = "email";
        } else if (sortField == UserSortFieldEnum.USERNAME) {
            fieldName = "username";
        }
        return fieldName;
    }
}
