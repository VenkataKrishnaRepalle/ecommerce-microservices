package com.spring6.ecommerce.service;

import com.spring6.ecommerce.commonutil.dto.BrandFindResponesDto;
import com.spring6.ecommerce.dto.UserCreateRequestDto;
import com.spring6.ecommerce.dto.UserCreateResponseDto;
import com.spring6.ecommerce.dto.UserUpdateRequestDto;
import com.spring6.ecommerce.dto.UserUpdateResponseDto;
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

//    public static final int BRANDS_PER_PAGE = 2;
//    private final UserRepository userRepository;
//    private final UserMapper userMapper;
//
//    public List<BrandFindResponesDto> findAll() {
//        return userRepository.findAll()
//                .stream()
//                .map(userMapper::brandToBrandFineResponesDto)
//                .toList();
//    }
//
//    public List<BrandFindResponesDto> findByPage(int pageNumber, String sortField, String sortDir, String keyword) {
//        Sort sort = Sort.by(sortField);
//        sort = sortDir.equals("ASC") ? sort.ascending() : sort.descending();
//
//        Pageable pageable = PageRequest.of(pageNumber - 1, BRANDS_PER_PAGE, sort);
//
//        if (keyword != null) {
//            return userRepository.findAll(keyword, pageable)
//                    .stream()
//                    .map(userMapper::brandToBrandFineResponesDto)
//                    .toList();
//        }
//        return userRepository.findAll(pageable)
//                .stream()
//                .map(userMapper::brandToBrandFineResponesDto)
//                .toList();
//
//    }
//
//    @Override
//    public BrandFindResponesDto findById(UUID id) throws BrandNotFoundException {
//        Optional<Brand> optionalBrand = userRepository.findById(id);
//
//        if (optionalBrand.isPresent()) {
//            return userMapper.brandToBrandFineResponesDto(optionalBrand.get());
//        }
//
//        throw new BrandNotFoundException("Could not find any brand with ID : " + id);
//    }
//
//    @Override
//    public Boolean isNameExist(String name) {
//        Optional<Brand> optionalBrand = userRepository.findByName(name);
//        if (optionalBrand.isPresent()) {
//            return Boolean.TRUE;
//        }
//        return Boolean.FALSE;
//    }
//
//    @Override
//    public UserCreateResponseDto create(UserCreateRequestDto brandCreateRequestDto) {
//        return userMapper.brandToBrandCreateResponseDto(
//                userRepository.save(userMapper.brandCreateRequestDtoToBrand(brandCreateRequestDto))
//        );
//
//    }
//
//    @Override
//    public UserUpdateResponseDto update(final UUID id, UserUpdateRequestDto brandCreateRequestDto)
//            throws BrandNotFoundException {
//        Optional<Brand> optionalBrand = userRepository.findById(id);
//
//        if (!optionalBrand.isPresent()) {
//            throw new BrandNotFoundException("Could not find any brand with ID : " + id);
//        }
//
//
//    }
//
//    @Override
//    public void deleteById(UUID id) throws BrandNotFoundException {
//        Long brandCountById = userRepository.countById(id);
//        if (brandCountById == 0) {
//            throw new BrandNotFoundException("Could not find any brand with ID : " + id);
//        }
//        userRepository.deleteById(id);
//    }

}
