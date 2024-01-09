package com.pm.spring.ema.shoppingcart.controller;

import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.StatusType;
import com.pm.spring.ema.common.util.api.SuccessResponse;
import com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.request.ShoppingCartCreateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.shoppingcartDto.response.ShoppingCartCreateResponseDto;
import com.pm.spring.ema.shoppingcart.exception.shoppingCartException.ShoppingCartAlreadyExistException;
import com.pm.spring.ema.shoppingcart.service.ShoppingCartService;
import com.pm.spring.ema.shoppingcart.utils.TraceIdHolder;
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
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/shopping-cart")
@Tag(name = "shopping-cart")
@RestController
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


        log.debug("ShoppingCartController:createShoppingCart execution started. traceId: {} request payload: {}", TraceIdHolder.getTraceId(), shoppingCartCreateRequestDto);

        ShoppingCartCreateResponseDto shoppingCartCreateResponseDto  = shoppingCartService.createShoppingCart(shoppingCartCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("ShoppingCartController:createShoppingCart execution ended. traceId: {} response: {}", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDto);

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


        log.debug("ShoppingCartController:getAllCarts execution started. traceId: {}", TraceIdHolder.getTraceId());

        List<ShoppingCartCreateResponseDto> shoppingCartCreateResponseDtoList  = shoppingCartService.getAllShoppingCarts();

        HttpHeaders headers = new HttpHeaders();

        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("ShoppingCartController:getAllCarts execution ended. traceId: {} response : {}", TraceIdHolder.getTraceId(),shoppingCartCreateResponseDtoList);


        return ResponseEntity.ok()
                .headers(headers)
                .body(shoppingCartCreateResponseDtoList);
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Shopping-cart found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShoppingCartCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Shopping-cart could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get a shopping-cart by id",description = "Get a shopping-cart by id")
    @GetMapping("{shoppingCartUuid}")
    public ResponseEntity<SuccessResponse> getShoppingCartById(@PathVariable final UUID shoppingCartUuid) {

        log.debug("ShoppingCartController:getShoppingCartById EXECUTION STARTED. traceId: {} request id: {}", TraceIdHolder.getTraceId(), shoppingCartUuid);

        ShoppingCartCreateResponseDto shoppingCartCreateResponseDto  = shoppingCartService.getShoppingCartById(shoppingCartUuid);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("ShoppingCartController:getShoppingCartById  EXECUTION ENDED. traceId: {} response : {}", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder().data(shoppingCartCreateResponseDto).status(StatusType.SUCCESS).build());

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Shopping-cart found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ShoppingCartCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Shopping-cart could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get a shopping-cart by customer-id",description = "Get a shopping-cart by customer-id")
    @GetMapping("/customer/{customerUuid}")
    public ResponseEntity<SuccessResponse> getShoppingCartByUserId(@PathVariable final UUID customerUuid) {

        log.debug("ShoppingCartController:getShoppingCartByUserId EXECUTION STARTED. traceId: {} request id: {}", TraceIdHolder.getTraceId(), customerUuid);

        ShoppingCartCreateResponseDto shoppingCartCreateResponseDto  = shoppingCartService.getShoppingCartByCustomerId(customerUuid);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("ShoppingCartController:getShoppingCartByUserId  EXECUTION ENDED. traceId: {} response : {}", TraceIdHolder.getTraceId(), shoppingCartCreateResponseDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder().data(shoppingCartCreateResponseDto).status(StatusType.SUCCESS).build());

    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Shopping-cart deleted.", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Shopping-cart could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Delete Shopping-cart by id",description = "Delete a Shopping-cart")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteShoppingCartById(@PathVariable final UUID id) {

        log.debug("ShoppingCartController:deleteShoppingCartById execution started. traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        shoppingCartService.deleteShoppingCartById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("ShoppingCartController:deleteShoppingCartById execution ended. traceId: {}", TraceIdHolder.getTraceId());
        return ResponseEntity.ok()
                .headers(headers)
                .build();

    }



}
