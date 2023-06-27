package com.spring6.user.controller;


import com.spring6.common.exeption.ErrorCodes;
import com.spring6.common.exeption.ErrorListResponse;
import com.spring6.common.exeption.ErrorResponse;
import com.spring6.common.utils.FileUploadUtils;
import com.spring6.common.utils.GlobalConstants;
import com.spring6.common.utils.HttpStatusCodes;
import com.spring6.common.utils.TraceIdHolder;
import com.spring6.user.dto.*;
import com.spring6.user.enums.UserSearchKeywordEnum;
import com.spring6.user.exception.UserNameAlreadyExistException;
import com.spring6.user.exception.UserNotFoundException;
import com.spring6.user.service.UserService;
import com.spring6.user.validations.ValidImageExtension;
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
import org.springframework.http.HttpStatus;
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

    @Operation(tags = "User", summary = "Create User", description = "Create a new User by entering user details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a User", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "create")
    public ResponseEntity<UserCreateResponseDto> createUser(
            @RequestBody @Valid final UserCreateRequestDto userCreateRequestDto)
            throws UserNameAlreadyExistException {
        log.info("UserController:createUser execution started.");
        log.debug("UserController:createUser traceId: {} request payload: {}", TraceIdHolder.traceId, userCreateRequestDto);

        UserCreateResponseDto savedUserDto = userService.createUser(userCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.traceId);

        log.debug("UserController:createUser traceId: {} response: {}", TraceIdHolder.traceId, savedUserDto);
        log.info("UserController:createUser execution ended.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(savedUserDto);
    }

    @Operation(tags = "User",
            summary = "Get User By Id",
            description = "Get User by id"

    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get User Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<UserFindResponseDto> getUserById(@PathVariable @Valid final UUID id) {
        log.info("UserController:getUserById execution started.");
        log.info("UserController:getUserById traceId: {} request id: {}", TraceIdHolder.traceId, id);

        UserFindResponseDto userFindResponseDto = userService.getUserById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.traceId);

        log.info("UserController:getUserById traceId: {} response : {}", TraceIdHolder.traceId, userFindResponseDto);
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
        log.info("UserController:getAllUsers traceId: {}", TraceIdHolder.traceId);
        List<UserFindResponseDto> userFindResponseDtoList = userService.getAllUsers();

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.traceId);

        log.info("UserController:getAllUsers traceId: {} response: {}", TraceIdHolder.traceId, userFindResponseDtoList);
        log.info("UserController:getAllUsers execution ended.");

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
        log.info("UserController:updateUser traceId: {} request id: {} payload: {}", TraceIdHolder.traceId, id, userUpdateRequestDto);

        UserUpdateResponseDto userUpdateResponseDto = userService.updateUser(id, userUpdateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.traceId);

        log.info("UserController:updateUser traceId: {} response: {}", TraceIdHolder.traceId, userUpdateResponseDto);
        log.info("UserController:updateUser ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(userUpdateResponseDto);

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
        log.info("UserController:deleteById traceId: {} request id: {}", TraceIdHolder.traceId, id);

        userService.deleteUserById(id);
        String dir = "../user-logos/" + id;
        FileUploadUtils.removeDir(dir);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.traceId);

        log.info("UserController:deleteById traceId: {}", TraceIdHolder.traceId);
        log.info("UserController:deleteById ended.");

        return ResponseEntity.noContent()
                .headers(headers)
                .build();
    }

    @Operation(tags = "User", summary = "Get Users By Pagination", description = "Get users by pagination by passing pagination attributes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Users", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("page")
    public ResponseEntity<List<UserFindResponseDto>> getUsersByPage(@RequestParam(name = "pageNumber") Integer pageNumber,
                                                                      @RequestParam("perPageCount") Integer perPageCount,
                                                                      @RequestParam("sortField") String sortField,
                                                                      @RequestParam("sortDirection") String sortDirection,
                                                                      @RequestParam("searchField") UserSearchKeywordEnum searchField,
                                                                      @RequestParam("searchKeyword") String searchKeyword) {
        log.info("UserController:getUsersByPage started.");
        log.info("UserController:getUsersByPage traceId: {} request pageNumber: {} perPageCount: {} sortField: {} sortDirection: {} searchField: {} searchKeyword: {}", TraceIdHolder.traceId, pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        List<UserFindResponseDto> userFindResponseDtoList = userService.getUsersByPage(pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.traceId);

        log.info("UserController:getUsersByPage traceId: {} response: {}", TraceIdHolder.traceId, userFindResponseDtoList);
        log.info("UserController:getUsersByPage ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(userFindResponseDtoList);
    }

    @Operation(tags = "User", summary = "Upload User Image", description = "Upload user image by passing user id and user image")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Image uploaded success"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("upload-image")
    public ResponseEntity<Void> uploadUserImage(
            @RequestParam @NotNull final UUID userId,
            @NotNull @ValidImageExtension @RequestParam("fileImage") final MultipartFile multipartFile)
            throws IOException, UserNameAlreadyExistException {
        log.info("UserController:uploadUserImage started.");
        log.info("UserController:uploadUserImage traceId: {} request id: {}", TraceIdHolder.traceId, userId);

        if (userService.isIdExist(userId)) {
            log.error("UserController:uploadUserImage traceId: {} User Not Found id: {}", TraceIdHolder.traceId, userId);
            throw new UserNotFoundException(ErrorCodes.E0507.getCode(), userId.toString());
        }

        if (!multipartFile.isEmpty() && multipartFile.getOriginalFilename() != null) {
            String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

            FileUploadUtils.cleanDir(IMAGE_UPLOAD_DIRECTORY);
            FileUploadUtils.saveFile(IMAGE_UPLOAD_DIRECTORY, fileName, multipartFile.getInputStream());

            userService.updateImageName(userId, fileName);

        } else {
            log.error("UserController:uploadUserImage traceId: {} File Not Found id: {}", TraceIdHolder.traceId, userId);
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.traceId);

        log.info("UserController:uploadUserImage traceId: {}", TraceIdHolder.traceId);
        log.info("UserController:uploadUserImage ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .build();
    }

    @Operation(tags = "User", summary = "Get User Image By Id", description = "Get user by passing user id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Success", content = {@Content(mediaType = "application/octet-stream")}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Image Not found"),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("image/{id}")
    public ResponseEntity<Resource> getUserImageById(
            @PathVariable("id") UUID id) throws MalformedURLException {
        log.info("UserController:getUserImageById started.");
        log.info("UserController:getUserImageById traceId: {} request id: {}", TraceIdHolder.traceId, id);

        UserFindResponseDto userDto = userService.getUserById(id);

        Path imagePath = Paths.get(IMAGE_UPLOAD_DIRECTORY, userDto.getPhoto());
        Resource imageResource = new UrlResource(imagePath.toUri());

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.traceId);

        if (!imageResource.exists()) {
            log.error("UserController:getUserImageById traceId: {} id: {} Image source not found", TraceIdHolder.traceId, id);
            log.error("UserController:getUserImageById ended.");
            return ResponseEntity.notFound()
                    .headers(headers).build();

        }
        headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + userDto.getPhoto() + "\"");

        log.info("UserController:getUserImageById traceId: {} id: {}", TraceIdHolder.traceId, id);
        log.info("UserController:getUserImageById ended.");

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .headers(headers)
                .body(imageResource);

    }
}
