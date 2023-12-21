package com.pm.spring.ema.controller;

import com.pm.spring.ema.dto.request.ClientCreateRequestDto;
import com.pm.spring.ema.dto.response.ClientCreateResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController("client")
public class ClientController {

    @PostMapping("create")
    public ResponseEntity<ClientCreateResponseDto> create(@RequestBody ClientCreateRequestDto clientCreateRequestDto) {
        return ResponseEntity.ok().build();
    }

    private final BrandService brandService;

    @Value("${file.upload_directory}")
    private String IMAGE_UPLOAD_DIRECTORY;

    @Operation(tags = "Brand", summary = "Create Brand", description = "Create a new Brand by entering brand details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Brand", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = BrandCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "create")
    public ResponseEntity<BrandCreateResponseDto> createBrand(
            @RequestBody @Valid final BrandCreateRequestDto brandCreateRequestDto)
            throws BrandNameAlreadyExistException {
        log.info("BrandController:createBrand execution started.");
        log.debug("BrandController:createBrand traceId: {} request payload: {}", TraceIdHolder.getTraceId(), brandCreateRequestDto);

        BrandCreateResponseDto savedBrandDto = brandService.create(brandCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("BrandController:createBrand traceId: {} response: {}", TraceIdHolder.getTraceId(), savedBrandDto);
        log.info("BrandController:createBrand execution ended.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(savedBrandDto);
    }
}
