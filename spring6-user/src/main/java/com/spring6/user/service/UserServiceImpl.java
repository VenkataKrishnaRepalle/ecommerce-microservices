package com.spring6.user.service;

import com.spring6.common.exeption.ErrorCodes;
import com.spring6.common.exeption.ErrorMessage;
import com.spring6.common.utils.TraceIdHolder;
import com.spring6.user.dto.*;
import com.spring6.user.entity.User;
import com.spring6.user.enums.UserSearchKeywordEnum;
import com.spring6.user.exception.UserNameAlreadyExistException;
import com.spring6.user.exception.UserNotFoundException;
import com.spring6.user.mapper.UserMapper;
import com.spring6.user.repository.UserRepository;
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

    public List<UserFindResponseDto> getAllUsers() {
        log.info("UserService:getAllUsers execution started.");
        log.debug("UserService:getAllUsers traceId: {}", TraceIdHolder.traceId);

        List<UserFindResponseDto> userFindResponseDtoList = userRepository.findAll()
                .stream()
                .map(userMapper::userToUserFindResponseDto)
                .toList();

        log.debug("UserService:findAll traceId: {}, response {} ", TraceIdHolder.traceId, userFindResponseDtoList);
        log.info("UserService:findAll execution ended.");

        return userFindResponseDtoList;
    }

    public List<UserFindResponseDto> getUsersByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDirectory, UserSearchKeywordEnum searchField, String searchKeyword) {
        log.info("UserService:getUsersByPage execution started.");
        log.debug("UserService:getUsersByPage traceId: {},  pageNumber: {}, perPageCount: {}, sortField: {}, sortDirectory: {}, searchField: {}, searchKeyword: {}", TraceIdHolder.traceId, pageNumber, perPageCount, sortField, sortDirectory, searchField, searchKeyword);

        if (sortField.isBlank()) {
            sortField = "first_name";

        }
        Sort sort = Sort.by(sortField);
        sort = sortDirectory.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);

        Page<User> userList;

        if (searchKeyword != null && searchField.equals(UserSearchKeywordEnum.FIRST_NAME)) {
            userList =  userRepository.findAllByFirstName(searchKeyword, pageable);

        } else {
            userList =  userRepository.findAll(pageable);
        }

        List<UserFindResponseDto> userFindResponseDtoList = userList.stream()
                .map(userMapper::userToUserFindResponseDto)
                .toList();

        log.debug("UserService:getUsersByPage traceId: {}", TraceIdHolder.traceId);
        log.info("UserService:getUsersByPage execution ended.");

        return userFindResponseDtoList;

    }

    @Override
    public UserFindResponseDto getUserById(UUID id) throws UserNotFoundException {
        log.info("UserService:getUserById execution started.");
        log.debug("UserService:getUserById traceId: {}, id: {}", TraceIdHolder.traceId, id);

        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            log.error("UserService:getUserById traceId: {}, errorMessage: User Not found", TraceIdHolder.traceId);
            log.info("UserService:getUserById execution ended.");
            throw new UserNotFoundException(ErrorCodes.E0501.getCode(), id.toString());
        }

        UserFindResponseDto userFindResponesDto = userMapper.userToUserFindResponseDto(optionalUser.get());

        log.debug("UserService:getUserById traceId: {}, response: {}", TraceIdHolder.traceId, userFindResponesDto);
        log.info("UserService:getUserById execution ended.");

        return userFindResponesDto;
    }

    @Override
    public UserCreateResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {
        log.info("UserService:createUser execution started.");
        log.debug("UserService:createUser traceId: {} , userCreateRequestDto: {}", TraceIdHolder.traceId, userCreateRequestDto);

        if (isUserNameExist(userCreateRequestDto.getUsername())) {
            log.error("UserService:createUser traceId: {}, errorMessage: {}", TraceIdHolder.traceId, ErrorMessage.message(ErrorCodes.E0506.getCode(), userCreateRequestDto.getUsername()));
            throw new UserNameAlreadyExistException(ErrorCodes.E0506.getCode(), userCreateRequestDto.getUsername());
        }

        User user = userMapper.userCreateRequestDtoToUser(userCreateRequestDto);
        User userCreated = userRepository.save(user);
        UserCreateResponseDto userCreateResponseDto = userMapper.userToUserCreateResponseDto(userCreated);

        log.debug("UserService:createUser traceId: {}, response: {}", TraceIdHolder.traceId, userCreateResponseDto);
        log.info("UserService:createUser execution ended.");

        return userCreateResponseDto;

    }

    @Override
    public UserUpdateResponseDto updateUser(final UUID id, UserUpdateRequestDto userUpdateRequestDto)
            throws UserNotFoundException {
        log.info("UserService:updateUser execution started.");
        log.debug("UserService:updateUser traceId: {}, id: {}, userCreateRequestDto: {}", TraceIdHolder.traceId, id, userUpdateRequestDto);


        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(ErrorCodes.E0502.getCode(), id.toString());
        }

        User user = userMapper.userUpdateRequestDtoToUser(userUpdateRequestDto);
        user.setId(optionalUser.get().getId());

        User userUpdated = userRepository.save(user);
        UserUpdateResponseDto userUpdateResponseDto = userMapper.userToUserUpdateResponseDto(userUpdated);

        log.debug("UserService:updateUser traceId: {}, response: {}", TraceIdHolder.traceId, userUpdateResponseDto);
        log.info("UserService:updateUser execution ended.");

        return userUpdateResponseDto;
    }

    @Override
    public void deleteUserById(UUID id) throws UserNotFoundException {
        log.info("UserService:deleteUserById execution started.");
        log.debug("UserService:deleteUserById traceId: {}, id: {}", TraceIdHolder.traceId, id);

        Long userCountById = userRepository.countById(id);
        if (userCountById == 0) {
            log.error("UserService:deleteUserById traceId: {}, errorMessage: {}", TraceIdHolder.traceId, ErrorMessage.message(ErrorCodes.E0503.getCode(), id.toString()));
            throw new UserNotFoundException(ErrorCodes.E0503.getCode(), id.toString());
        }

        userRepository.deleteById(id);
        log.info("UserService:deleteUserById execution ended.");

    }

    @Override
    public String updateImageName(UUID userId, String fileName) {
        log.info("UserService:updateImageName execution started.");
        log.debug("UserService:updateImageName traceId: {}, userId:{}, fileName: {}", TraceIdHolder.traceId, userId, fileName);

        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            log.error("UserService:updateImageName traceId: {}, errorMessage:{}", TraceIdHolder.traceId, ErrorMessage.message(ErrorCodes.E0504.getCode(), userId.toString()));
            throw new UserNotFoundException(ErrorCodes.E0504.getCode(), userId.toString());
        }

        User user = optionalUser.get();
        user.setPhoto(fileName);

        User userUpdated = userRepository.save(user);

        log.debug("UserService:updateImageName traceId: {}, updatedImageName: {}", TraceIdHolder.traceId, userUpdated.getPhoto());
        log.info("UserService:updateImageName execution ended.");

        return userUpdated.getPhoto();
    }
    @Override
    public Boolean isUserNameExist(String username) {
        log.info("UserService:isNameExist execution started. traceId: {}", TraceIdHolder.traceId);

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isIdExist(UUID uuid) {
        log.info("UserService:isIdExist execution started. traceId: {}", TraceIdHolder.traceId);
        log.debug("UserService:isIdExist traceId: {}, id: {}", TraceIdHolder.traceId, uuid);

        Optional<User> optionalUser = userRepository.findById(uuid);
        if (optionalUser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
