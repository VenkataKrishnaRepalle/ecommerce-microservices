package com.pm.service.controller;

import com.pm.spring.ema.common.exeption.ErrorResponse;
import com.pm.spring.ema.common.utils.HttpStatusCodes;
import com.pm.service.dto.response.UserFindResponseDto;
import com.pm.service.export.UserCSVExporter;
import com.pm.service.export.UserExcelExporter;
import com.pm.service.export.UserPDFExporter;
import com.pm.service.service.UserService;
import com.pm.service.filter.TraceIdHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/user/export")
public class UserExportController {

    private final UserService userService;
    private final UserCSVExporter userCSVExporter;
    private final UserExcelExporter userExcelExporter;
    private final UserPDFExporter userPDFExporter;
    private final HttpServletResponse httpServletResponse;

    @Operation(tags = "User", summary = "Export All Users to CSV", description = "Export all users to csv format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get User Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserFindResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("csv")
    public void exportAllUsersToCSV() throws IOException {
        log.info("UserController:exportAllUsersToCSV started.");
        log.info("UserController:exportAllUsersToCSV traceId: {}", TraceIdHolder.getTraceId());
        List<UserFindResponseDto> userFindResponseDtoList = userService.getAll();

        userCSVExporter.export(userFindResponseDtoList, httpServletResponse);

        log.info("UserController:exportAllUsersToCSV execution ended.");
    }

    @Operation(tags = "User", summary = "Export All Users to Excel", description = "Export all users to xlsx format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get User Response"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("excel")
    public void exportAllUsersToExcel() throws IOException {
        log.info("UserController:exportAllUsersToExcel started.");
        log.info("UserController:exportAllUsersToExcel traceId: {}", TraceIdHolder.getTraceId());
        List<UserFindResponseDto> userFindResponseDtoList = userService.getAll();

        userExcelExporter.export(userFindResponseDtoList, httpServletResponse);

        log.info("UserController:exportAllUsersToExcel execution ended.");
    }

    @Operation(tags = "User", summary = "Export All Users to PDF", description = "Export all users to pdf format.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get User Response"),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "User not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("pdf")
    public void exportAllUsersToPdf() throws IOException {
        log.info("UserController:exportAllUsersToExcel started.");
        log.info("UserController:exportAllUsersToExcel traceId: {}", TraceIdHolder.getTraceId());
        List<UserFindResponseDto> userFindResponseDtoList = userService.getAll();

        userPDFExporter.export(userFindResponseDtoList, httpServletResponse);

        log.info("UserController:exportAllUsersToExcel execution ended.");
    }
}
