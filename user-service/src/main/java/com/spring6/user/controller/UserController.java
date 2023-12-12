package com.pm.spring.ema.user.controller;

import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.pm.spring.ema.common.exeption.ErrorListResponse;
import com.pm.spring.ema.common.exeption.ErrorResponse;
import com.pm.spring.ema.common.utils.FileNameUtil;
import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.common.utils.GlobalConstants;
import com.pm.spring.ema.common.utils.HttpStatusCodes;
import com.pm.spring.ema.user.dto.request.UserUpdateRequestDto;
import com.pm.spring.ema.user.dto.response.UserCreateResponseDto;
import com.pm.spring.ema.user.dto.response.UserFindResponseDto;
import com.pm.spring.ema.user.dto.response.UserUpdateResponseDto;
import com.pm.spring.ema.user.dto.enums.SortOrderDirectionEnum;
import com.pm.spring.ema.user.dto.enums.UserSearchKeywordEnum;
import com.pm.spring.ema.user.dto.enums.UserSortFieldEnum;
import com.pm.spring.ema.user.model.enums.UserStatus;
import com.pm.spring.ema.user.exception.UserNotFoundException;
import com.pm.spring.ema.user.service.UserService;
import com.pm.spring.ema.user.filter.TraceIdHolder;
import com.pm.spring.ema.user.dto.validations.ValidImageExtension;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user")
public class UserController {
    private final UserService userService;


    @Value("${file.upload-directory}")
    private String IMAGE_UPLOAD_DIRECTORY;

//    @Operation(tags = "User", summary = "Create User", description = "Create a new User by entering user details")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a User", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserCreateResponseDto.class))}),
//            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
//            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
//    })
//
//    @PostMapping(value = "create")
//    public ResponseEntity<UserCreateResponseDto> createUser(
//            @RequestBody @Valid final UserCreateRequestDto userCreateRequestDto)
//            throws UserNameAlreadyExistException {
//        log.info("UserController:createUser execution started.");
//        log.debug("UserController:createUser traceId: {} request payload: {}", TraceIdHolder.getTraceId(), userCreateRequestDto);
//
//        if (!userCreateRequestDto.getPassword().equals(userCreateRequestDto.getConfirmPassword())) {
//            log.error("UserController:createUser traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorCodes.E4008);
//            throw new PasswordMismatchException(ErrorCodes.E4008);
//        }
//
//        UserCreateResponseDto savedUserDto = userService.create(userCreateRequestDto);
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());
//
//        log.debug("UserController:createUser traceId: {} response: {}", TraceIdHolder.getTraceId(), savedUserDto);
//        log.info("UserController:createUser execution ended.");
//
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .headers(headers)
//                .body(savedUserDto);
//    }

    @Operation(tags = "User", summary = "Get User By Id", description = "Get User by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get User Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<UserFindResponseDto> getUserById(@PathVariable @Valid final UUID id) {
        log.info("UserController:getUserById execution started.");
        log.info("UserController:getUserById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        UserFindResponseDto userFindResponseDto = userService.getById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("UserController:getUserById traceId: {} response : {}", TraceIdHolder.getTraceId(), userFindResponseDto);
        log.info("UserController:getUserById execution ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(userFindResponseDto);
    }

    @Operation(tags = "User", summary = "Get All Users", description = "Get all users by passing user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get User Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("list")
    public ResponseEntity<List<UserFindResponseDto>> getAllUsers() {
        log.info("UserController:getAllUsers started.");
        log.info("UserController:getAllUsers traceId: {}", TraceIdHolder.getTraceId());
        List<UserFindResponseDto> userFindResponseDtoList = userService.getAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("UserController:getAllUsers traceId: {} response: {}", TraceIdHolder.getTraceId(), userFindResponseDtoList);
        log.info("UserController:getAllUsers execution ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(userFindResponseDtoList);
    }

    @Operation(tags = "User", summary = "Get User Image By Id", description = "Get user by passing user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Success", content = {@Content(mediaType = "application/octet-stream")}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Image Not found"),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}/image")
    public ResponseEntity<Resource> getUserImageById(
            @PathVariable("id") UUID id) throws MalformedURLException {
        log.info("UserController:getUserImageById started.");
        log.info("UserController:getUserImageById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        String imageName = userService.getPhotoById(id);

        Path imagePath = Paths.get(IMAGE_UPLOAD_DIRECTORY).resolve(imageName).normalize();
        Resource imageResource = new UrlResource(imagePath.toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        if (!imageResource.exists()) {
            log.error("UserController:getUserImageById traceId: {} id: {} Image source not found", TraceIdHolder.getTraceId(), id);
            log.error("UserController:getUserImageById ended.");
            return ResponseEntity.notFound()
                    .headers(headers).build();

        }
        String extension = FileNameUtil.getFileExtension(imageName);

        if (extension.equals("png")) {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);

        } else if (extension.equals("jpg") || extension.equals("jpeg")) {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_JPEG_VALUE);
        } else {
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
        }

        log.info("UserController:getUserImageById traceId: {} id: {}", TraceIdHolder.getTraceId(), id);
        log.info("UserController:getUserImageById ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(imageResource);

    }


    @Operation(tags = "User", summary = "Download User Profile Image By Id", description = "Download user profile image by passing user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Success", content = {@Content(mediaType = "application/octet-stream")}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Image Not found"),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}/image/download")
    public ResponseEntity<Resource> downloadUserImageById(
            @PathVariable("id") UUID id) throws MalformedURLException {
        log.info("UserController:downloadUserImageById started.");
        log.info("UserController:downloadUserImageById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        String imageName = userService.getPhotoById(id);

        Path imagePath = Paths.get(IMAGE_UPLOAD_DIRECTORY, imageName);
        Resource imageResource = new UrlResource(imagePath.toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        if (!imageResource.exists()) {
            log.error("UserController:downloadUserImageById traceId: {} id: {} Image source not found", TraceIdHolder.getTraceId(), id);
            log.error("UserController:downloadUserImageById ended.");
            return ResponseEntity.notFound()
                    .headers(headers).build();

        }
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageName + "\"");

        log.info("UserController:downloadUserImageById traceId: {} id: {}", TraceIdHolder.getTraceId(), id);
        log.info("UserController:downloadUserImageById ended.");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .headers(headers)
                .body(imageResource);

    }

    @Operation(tags = "User", summary = "Find the duplicate Email", description = "Find the duplicate email while register the user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get User Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("duplicate-email/{email}")
    public ResponseEntity<Boolean> isUserEmailExist(@PathVariable @Valid final String email) {
        log.info("UserController:isUserEmailExist execution started.");
        log.info("UserController:isUserEmailExist traceId: {} request email: {}", TraceIdHolder.getTraceId(), email);

        Boolean userEmailExist = userService.isEmailExist(email);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("UserController:isUserEmailExist traceId: {} response : {}", TraceIdHolder.getTraceId(), userEmailExist);
        log.info("UserController:isUserEmailExist execution ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(userEmailExist);
    }

    @Operation(tags = "User", summary = "Get Users By Pagination", description = "Get users by pagination by passing pagination attributes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("page")
    public ResponseEntity<List<UserFindResponseDto>> getUsersByPage(@RequestParam(value = "pageNumber", required = true) Integer pageNumber,
                                                                    @RequestParam(value = "perPageCount", required = true) Integer perPageCount,
                                                                    @RequestParam(value = "sortField", required = false) UserSortFieldEnum sortField,
                                                                    @RequestParam(value = "sortDirection", required = false) SortOrderDirectionEnum sortDirection,
                                                                    @RequestParam(value = "searchField", required = false) UserSearchKeywordEnum searchField,
                                                                    @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        log.info("UserController:getUsersByPage started.");
        log.info("UserController:getUsersByPage traceId: {} request pageNumber: {} perPageCount: {} sortField: {} sortDirection: {} searchField: {} searchKeyword: {}", TraceIdHolder.getTraceId(), pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        List<UserFindResponseDto> userFindResponseDtoList = userService.getByPage(pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("UserController:getUsersByPage traceId: {} response: {}", TraceIdHolder.getTraceId(), userFindResponseDtoList);
        log.info("UserController:getUsersByPage ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(userFindResponseDtoList);
    }


    @Operation(tags = "User", summary = "Update User", description = "Update user by passing user id and user request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a User", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("update/{id}")
    public ResponseEntity<UserUpdateResponseDto> updateUser(@PathVariable UUID id, @RequestBody UserUpdateRequestDto userUpdateRequestDto) {

        log.info("UserController:updateUser started.");
        log.info("UserController:updateUser traceId: {} request id: {} payload: {}", TraceIdHolder.getTraceId(), id, userUpdateRequestDto);

        UserUpdateResponseDto userUpdateResponseDto = userService.update(id, userUpdateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("UserController:updateUser traceId: {} response: {}", TraceIdHolder.getTraceId(), userUpdateResponseDto);
        log.info("UserController:updateUser ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(userUpdateResponseDto);

    }

    @Operation(tags = "User", summary = "Update User Enabled Status", description = "Update user status by passing user id and user status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a User", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Boolean.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("update/{id}/status/{status}")
    public ResponseEntity<Boolean> updateUserStatus(@PathVariable UUID id, @PathVariable UserStatus status) {

        log.info("UserController:updateUser started.");
        log.info("UserController:updateUser traceId: {} request id: {} user status: {}", TraceIdHolder.getTraceId(), id, status);

        Boolean isUserStatusUpdated = userService.updateUserStatus(id, status);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("UserController:updateUser traceId: {} response: {}", TraceIdHolder.getTraceId(), isUserStatusUpdated);
        log.info("UserController:updateUser ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(isUserStatusUpdated);

    }

    @Operation(tags = "User", summary = "Delete User By Id", description = "Delete existing user by passing user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a User", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable final UUID id) {
        log.info("UserController:deleteById started.");
        log.info("UserController:deleteById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        userService.deleteById(id);
        String dir = "../user-logos/" + id;
        FileUploadUtils.removeDir(dir);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("UserController:deleteById traceId: {}", TraceIdHolder.getTraceId());
        log.info("UserController:deleteById ended.");

        return ResponseEntity.noContent()
                .headers(headers)
                .build();
    }

    @Operation(tags = "User", summary = "Upload User Image", description = "Upload user image by passing user id and user image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Image uploaded success"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "upload-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadUserImage(
            @RequestParam @NotNull final UUID userId,
            @RequestPart("fileImage") @NotNull @ValidImageExtension final MultipartFile multipartFile)
            throws IOException {
        log.info("UserController:uploadUserImage started.");
        log.info("UserController:uploadUserImage traceId: {} request id: {}", TraceIdHolder.getTraceId(), userId);

        if (!userService.isIdExist(userId)) {
            log.error("UserController:uploadUserImage traceId: {} User Not Found id: {}", TraceIdHolder.getTraceId(), userId);
            throw new UserNotFoundException(ErrorCodes.E4507, userId.toString());
        }

        if (!multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            FileUploadUtils.cleanDir(IMAGE_UPLOAD_DIRECTORY);
            FileUploadUtils.saveFile(IMAGE_UPLOAD_DIRECTORY, fileName, multipartFile.getInputStream());

            userService.updateImageName(userId, fileName);

        } else {
            log.error("UserController:uploadUserImage traceId: {} File Not Found id: {}", TraceIdHolder.getTraceId(), userId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("UserController:uploadUserImage traceId: {}", TraceIdHolder.getTraceId());
        log.info("UserController:uploadUserImage ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }


//  Assign roles to a user.
//    @PostMapping("{userId}/roles")
//    Remove a role from a user.
//    @DeleteMapping("{userId}/role/{roleId}")

}
