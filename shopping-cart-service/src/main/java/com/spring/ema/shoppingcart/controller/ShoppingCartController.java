package com.spring.ema.shoppingcart.controller;

import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.StatusType;
import com.pm.spring.ema.common.util.api.SuccessResponse;
import com.spring.ema.shoppingcart.dto.shoppingcartDto.request.ShoppingCartCreateRequestDto;
import com.spring.ema.shoppingcart.dto.shoppingcartDto.response.ShoppingCartCreateResponseDto;
import com.spring.ema.shoppingcart.exception.shoppingCartException.ShoppingCartAlreadyExistException;
import com.spring.ema.shoppingcart.service.ShoppingCartService;
import com.spring.ema.shoppingcart.utils.TraceIdHolder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("api/shopping-cart")
@Tag(name = "shopping-cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Create a new cart", description = "Add a new cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cart created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShoppingCartCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Cart already exist", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("create")
    public ResponseEntity<SuccessResponse> createShoppingCart(@RequestBody @Valid final ShoppingCartCreateRequestDto shoppingCartCreateRequestDto) throws ShoppingCartAlreadyExistException {

        log.info("ShoppingCartController:createShoppingCart execution started.");
        log.debug("ShoppingCartController:createShoppingCart traceId: {} request payload: {}", TraceIdHolder.getTraceId(), shoppingCartCreateRequestDto);

        ShoppingCartCreateResponseDto shoppingCartCreateResponseDto  = shoppingCartService.createShoppingCart(shoppingCartCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("ShoppingCartController:createShoppingCart traceId: {} response: {}", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDto);
        log.info("ShoppingCartController:createShoppingCart execution ended.");

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(SuccessResponse.builder()
                        .data(shoppingCartCreateResponseDto)
                        .status(StatusType.SUCCESS)
                        .build());
    }

    @Operation(summary = "Get all carts", description = "Fetch all the carts")
    @GetMapping("list")
    public ResponseEntity<List<ShoppingCartCreateResponseDto>> getAllCarts() {

        log.info("ShoppingCartController:getAllCarts started.");

        log.info("ShoppingCartController:getAllCarts traceId: {}", TraceIdHolder.getTraceId());

        List<ShoppingCartCreateResponseDto> shoppingCartCreateResponseDtoList  = shoppingCartService.getAllShoppingCarts();

        HttpHeaders headers = new HttpHeaders();

        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("ShoppingCartController:getAllCarts traceId: {} response : {}", TraceIdHolder.getTraceId(),shoppingCartCreateResponseDtoList);

        log.info("ShoppingCartController:getAllCarts execution ended.");

        return ResponseEntity.ok()
                .headers(headers)
                .body(shoppingCartCreateResponseDtoList);
    }


}
