package com.spring6.ecommerce.service;

import com.spring6.ecommerce.dto.*;
import com.spring6.ecommerce.entity.User;
import com.spring6.ecommerce.exception.UserNotFoundException;
import com.spring6.ecommerce.mapper.UserMapper;
import com.spring6.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final int COUNT_PER_PAGE = 2;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public List<UserFindResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::userToUserFindResponseDto)
                .toList();
    }

    @Override
    public Boolean isUsernameExist(String username) {
        Optional<User> optionaluser = userRepository.findByUsername(username);
        if (optionaluser.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    @Override
    public UserCreateResponseDto create(UserCreateRequestDto userCreateRequestDto) {
        return userMapper.userToUserCreateResponseDto(
                userRepository.save(userMapper.userCreateRequestDtoToUser(userCreateRequestDto))
        );

    }

    @Override
    public List<UserFindResponseDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, COUNT_PER_PAGE, sort);

        if (keyword != null) {
            return userRepository.findAll(keyword, pageable)
                    .stream()
                    .map(userMapper::userToUserFindResponseDto)
                    .toList();
        }
        return userRepository.findAll(pageable)
                .stream()
                .map(userMapper::userToUserFindResponseDto)
                .toList();

    }

    @Override
    public UserFindResponseDto findById(UUID id) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            return userMapper.userToUserFindResponseDto(optionalUser.get());
        }

        throw new UserNotFoundException("Could not find any user with ID : " + id);
    }

    @Override
    public UserUpdateResponseDto update(final UUID id, UserUpdateRequestDto userUpdateRequestDto)
            throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findById(id);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("Could not find any user with ID : " + id);
        }

        User user = userMapper.userUpdateRequestDtoToUser(userUpdateRequestDto);
        user.setId(optionalUser.get().getId());

        User userSaved = userRepository.save(user);
        return userMapper.userToUserUpdateResponseDto(userSaved);
    }

    @Override
    public void deleteById(UUID id) throws UserNotFoundException {
        Long userCountById = userRepository.countById(id);
        if (userCountById == 0) {
            throw new UserNotFoundException("Could not find any user with ID : " + id);
        }
        userRepository.deleteById(id);
    }

}
