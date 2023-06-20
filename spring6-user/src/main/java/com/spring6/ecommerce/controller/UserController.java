package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.exception.UsernameAlreadyExistException;
import com.spring6.ecommerce.service.UserService;
import com.spring6.ecommerce.common.utils.FileUploadUtils;
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
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("user")
public class UserController {

    private final UserService userService;


    @GetMapping("page/{pageNumber}")
    public List<UserFindResponseDto> findByPage(@PathVariable(name = "pageNumber") int pageNumber,
                                                @RequestParam("sortField") String sortField,
                                                @RequestParam("sortDir") String sortDir,
                                                @RequestParam("keyword") String keyword) {
        return userService.findByPage(pageNumber, sortField, sortDir, keyword);
    }

    @GetMapping("list")
    public List<UserFindResponseDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("{userId}")
    public UserFindResponseDto getById(@PathVariable final UUID userId) {
        return userService.findById(userId);
    }

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
            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile.getInputStream());
        }


        UserCreateResponseDto savedBrandDto = userService.create(userCreateRequestDto);


        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/brand" + savedBrandDto.getId().toString());

        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }

    @PatchMapping("update/{id}")
    public UserUpdateResponseDto update(@PathVariable UUID id, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {
        return UserUpdateResponseDto.builder().build();
    }

    @DeleteMapping("delete/{userId}")
    public void deleteById(@PathVariable final UUID userId) {
        userService.deleteById(userId);
        String brandDir = "../brand-logos/" + userId;
        FileUploadUtils.removeDir(brandDir);
    }

}
