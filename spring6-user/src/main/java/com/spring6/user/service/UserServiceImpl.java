package com.spring6.user.service;

import com.spring6.common.exeption.ErrorCodes;
import com.spring6.common.exeption.ErrorMessage;
import com.spring6.user.dto.*;
import com.spring6.user.entity.User;
import com.spring6.user.enums.SortOrderDirectionEnum;
import com.spring6.user.enums.UserSearchKeywordEnum;
import com.spring6.user.enums.UserSortFieldEnum;
import com.spring6.user.enums.UserStatus;
import com.spring6.user.exception.UserEmailAlreadyExistException;
import com.spring6.user.exception.UserNameAlreadyExistException;
import com.spring6.user.exception.UserNotFoundException;
import com.spring6.user.exception.UserPhotoNotFoundException;
import com.spring6.user.mapper.UserMapper;
import com.spring6.user.repository.UserRepository;
import com.spring6.user.utils.TraceIdHolder;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
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
//    private final PasswordEncoder passwordEncoder;
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

        Page<User> userList;

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

        Optional<User> optionalUser = userRepository.findById(id);

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

        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            log.error("UserService:getPhotoById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4513, id.toString()));
            log.info("UserService:getPhotoById execution ended.");
            throw new UserNotFoundException(ErrorCodes.E4513, id.toString());
        }

        User user = optionalUser.get();

        if (user.getPhoto() == null) {
            log.error("UserService:getPhotoById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4514, id.toString()));
            log.info("UserService:getPhotoById execution ended.");
            throw new UserPhotoNotFoundException(ErrorCodes.E4514, id.toString());
        }

        log.debug("UserService:getPhotoById traceId: {}, photo : {}", TraceIdHolder.getTraceId(), user.getPhoto());
        log.info("UserService:getPhotoById execution ended.");
        return user.getPhoto();
    }

    @Override
    public UserCreateResponseDto create(UserCreateRequestDto userCreateRequestDto) {
        log.info("UserService:createUser execution started.");
        log.debug("UserService:createUser traceId: {} , userCreateRequestDto: {}", TraceIdHolder.getTraceId(), userCreateRequestDto);

        if (isUsernameExist(userCreateRequestDto.getUsername())) {
            log.error("UserService:createUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4506, userCreateRequestDto.getUsername()));
            throw new UserNameAlreadyExistException(ErrorCodes.E4506, userCreateRequestDto.getUsername());
        }

        if (isEmailExist(userCreateRequestDto.getEmail())) {
            log.error("UserService:createUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4509, userCreateRequestDto.getEmail()));
            throw new UserEmailAlreadyExistException(ErrorCodes.E4509, userCreateRequestDto.getEmail());
        }

        User user = userMapper.userCreateRequestDtoToUser(userCreateRequestDto);

//        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userCreated = userRepository.save(user);
        UserCreateResponseDto userCreateResponseDto = userMapper.userToUserCreateResponseDto(userCreated);

        log.debug("UserService:createUser traceId: {}, response: {}", TraceIdHolder.getTraceId(), userCreateResponseDto);
        log.info("UserService:createUser execution ended.");

        return userCreateResponseDto;

    }

    @Override
    public UserUpdateResponseDto update(final UUID id, UserUpdateRequestDto userUpdateRequestDto)
            throws UserNotFoundException {
        log.info("UserService:updateUser execution started.");
        log.debug("UserService:updateUser traceId: {}, id: {}, userCreateRequestDto: {}", TraceIdHolder.getTraceId(), id, userUpdateRequestDto);

        Optional<User> optionalUser = userRepository.findById(id);

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

        User user = userMapper.userUpdateRequestDtoToUser(userUpdateRequestDto);
        user.setId(optionalUser.get().getId());

        User userUpdated = userRepository.save(user);
        UserUpdateResponseDto userUpdateResponseDto = userMapper.userToUserUpdateResponseDto(userUpdated);

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

        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            log.error("UserService:updateImageName traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4504, userId.toString()));
            throw new UserNotFoundException(ErrorCodes.E4504, userId.toString());
        }

        User user = optionalUser.get();
        user.setPhoto(fileName);

        User userUpdated = userRepository.save(user);

        log.debug("UserService:updateImageName traceId: {}, updatedImageName: {}", TraceIdHolder.getTraceId(), userUpdated.getPhoto());
        log.info("UserService:updateImageName execution ended.");

        return userUpdated.getPhoto();
    }

    @Override
    public Boolean isEmailExist(String email) {
        log.info("UserService:isUserEmailExist execution started.");
        log.debug("UserService:isUserEmailExist traceId: {}, username: {}", TraceIdHolder.getTraceId(), email);

        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;

    }

    @Override
    public Boolean updateUserStatus(UUID id, UserStatus userStatus) {

        log.info("UserService:updateUserStatus execution started.");
        log.debug("UserService:updateUserStatus traceId: {}, id:{}, userStatus: {}", TraceIdHolder.getTraceId(), id, userStatus);

        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            log.error("UserService:updateUserStatus traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E4512, id.toString()));
            throw new UserNotFoundException(ErrorCodes.E4512, id.toString());
        }

        User user = optionalUser.get();
        user.setStatus(userStatus);

        User userUpdated = userRepository.save(user);

        if (userUpdated == null) {
            return Boolean.FALSE;
        }

        log.debug("UserService:updateUserStatus traceId: {}, userStatus: {}", TraceIdHolder.getTraceId(), userUpdated.getStatus());
        log.info("UserService:updateUserStatus execution ended.");

        return Boolean.TRUE;

    }

    @Override
    public Boolean isUsernameExist(String username) {
        log.info("UserService:isUserNameExist execution started.");
        log.debug("UserService:isUserNameExist traceId: {}, username: {}", TraceIdHolder.getTraceId(), username);

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isIdExist(UUID uuid) {
        log.info("UserService:isIdExist execution started.");
        log.debug("UserService:isIdExist traceId: {}, id: {}", TraceIdHolder.getTraceId(), uuid);

        Optional<User> optionalUser = userRepository.findById(uuid);
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
