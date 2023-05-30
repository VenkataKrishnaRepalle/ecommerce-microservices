package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.dto.UserCreateRequestDto;
import com.spring6.ecommerce.dto.UserCreateResponseDto;
import com.spring6.ecommerce.dto.UserFindResponseDto;
import com.spring6.ecommerce.exception.UsernameAlreadyExistException;
import com.spring6.ecommerce.service.UserService;
import com.spring6.ecommerce.utils.FileUploadUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;

//
//    @GetMapping("page/{pageNumber}")
//    public List<BrandFineResponesDto> findByPage(@PathVariable(name = "pageNumber") int pageNumber,
//                                                 @RequestParam("sortField") String sortField,
//                                                 @RequestParam("sortDir") String sortDir,
//                                                 @RequestParam("keyword") String keyword) {
//        return userService.findByPage(pageNumber, sortField, sortDir, keyword);
//    }
//
    @GetMapping("list")
    public List<UserFindResponseDto> findAll() {
        return userService.findAll();
    }
//
//    @GetMapping("{brandId}")
//    public BrandFineResponesDto getById(@PathVariable final UUID brandId) {
//        return userService.findById(brandId);
//    }
//
    @PostMapping("create")
    public ResponseEntity<HttpHeaders> create(
            @RequestBody @Valid final UserCreateRequestDto userCreateRequestDto,
            @RequestParam("fileImage") final MultipartFile multipartFile)
            throws IOException, UsernameAlreadyExistException {

        if (userService.isUsernameExist(userCreateRequestDto.getUsername())) {
            throw new UsernameAlreadyExistException("Brand name :" + userCreateRequestDto.getUsername() + " already " +
                    "exist");
        }

        if (!multipartFile.isEmpty()) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
            userCreateRequestDto.setPhoto(fileName);

            String uploadDir = "../brand-logos";

            FileUploadUtils.cleanDir(uploadDir);
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
        }


        UserCreateResponseDto savedBrandDto = userService.create(userCreateRequestDto);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/brand" + savedBrandDto.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
//
//    @PatchMapping("update/{id}")
//    public UserUpdateResponseDto update(@PathVariable UUID id, @RequestBody UserUpdateRequestDto brandDto) {
//        return UserUpdateResponseDto.builder().build();
//    }
//
//    @DeleteMapping("delete/{brandId}")
//    public void deleteById(@PathVariable final UUID brandId) {
//        userService.deleteById(brandId);
//        String brandDir = "../brand-logos/" + brandId;
//        FileUploadUtils.removeDir(brandDir);
//    }

}
