package com.spring6.ecommerce.controller;

import com.spring6.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("brand")
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
//    @GetMapping("list")
//    public List<BrandFineResponesDto> findAll() {
//        return userService.findAll();
//    }
//
//    @GetMapping("{brandId}")
//    public BrandFineResponesDto getById(@PathVariable final UUID brandId) {
//        return userService.findById(brandId);
//    }
//
//    @PostMapping("create")
//    public ResponseEntity<HttpHeaders> create(
//            @RequestBody @Valid final UserCreateRequestDto brandCreateRequestDto,
//            @RequestParam("fileImage") final MultipartFile multipartFile)
//            throws IOException, UserNameAlreadyExistException {
//
//        if (userService.isNameExist(brandCreateRequestDto.getName())) {
//            throw new BrandNameAlreadyExistException("Brand name :" + brandCreateRequestDto.getName() + " already exist");
//        }
//
//        if (!multipartFile.isEmpty()) {
//            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
//            brandCreateRequestDto.setLogo(fileName);
//
//            String uploadDir = "../brand-logos";
//
//            FileUploadUtils.cleanDir(uploadDir);
//            FileUploadUtils.saveFile(uploadDir, fileName, multipartFile);
//        }
//
//
//        UserCreateResponseDto savedBrandDto = userService.create(brandCreateRequestDto);
//
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Location", "/brand" + savedBrandDto.getId().toString());
//
//        return new ResponseEntity<>(headers, HttpStatus.CREATED);
//    }
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
