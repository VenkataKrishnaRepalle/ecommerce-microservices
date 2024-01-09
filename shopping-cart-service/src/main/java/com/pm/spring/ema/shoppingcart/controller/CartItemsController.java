package com.pm.spring.ema.shoppingcart.controller;

import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.api.StatusType;
import com.pm.spring.ema.common.util.api.SuccessResponse;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request.CartItemsCreateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.request.CartItemsUpdateRequestDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.response.CartItemsResponseDto;
import com.pm.spring.ema.shoppingcart.dto.cartItemsDto.response.CartItemsUpdateResponseDto;
import com.pm.spring.ema.shoppingcart.service.CartItemsService;
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
@RestController
@RequestMapping("api/cart-items")
@Tag(name = "cart-items")
public class CartItemsController {

    private final CartItemsService cartItemsService;

    @Operation(summary = "Create a new cart item", description = "Add a new item to cart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Item created", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CartItemsResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("create")
    public ResponseEntity<SuccessResponse> createCartItems(@RequestBody @Valid final CartItemsCreateRequestDto cartItemsCreateRequestDto)  {


        log.debug("CartItemsController:createCartItems execution started. traceId: {} request payload: {}", TraceIdHolder.getTraceId(), cartItemsCreateRequestDto);

        CartItemsResponseDto cartItemsResponseDto = cartItemsService.createCartItems(cartItemsCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("CartItemsController:createCartItems execution ended. traceId: {} response: {}", TraceIdHolder.getTraceId(), cartItemsResponseDto);

        return ResponseEntity.status(HttpStatus.CREATED)
                .headers(headers)
                .body(SuccessResponse.builder()
                        .data(cartItemsResponseDto)
                        .status(StatusType.SUCCESS)
                        .build());
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cart-item found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CartItemsResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Cart-item  could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get  cart-items by shoppingCartId",description = "Get all cart-items by shoppingCartId")
    @GetMapping("{shoppingCartId}")
    public ResponseEntity<SuccessResponse> getCartItemsByShoppingCartId(@PathVariable final UUID shoppingCartId) {

        log.debug("CartItemsController:getCartItemsByShoppingCartId EXECUTION STARTED. traceId: {} request id: {}", TraceIdHolder.getTraceId(), shoppingCartId);

       List <CartItemsResponseDto> cartItemsResponseDto =  cartItemsService.getCartItemsByShoppingCartId(shoppingCartId);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("CartItemsController:getCartItemsByShoppingCartId  EXECUTION ENDED. traceId: {} response : {}", TraceIdHolder.getTraceId(), cartItemsResponseDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder().data(cartItemsResponseDto).status(StatusType.SUCCESS).build());

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cart-items found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CartItemsResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Cart-items could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get all Cart-items", description = "Fetch all the Cart-items")
    @GetMapping("list")
    public ResponseEntity<SuccessResponse> getAllCartItems() {

        log.debug("CartItemsController:getAllCartItems EXECUTION STARTED. traceId: {}", TraceIdHolder.getTraceId());

        List<CartItemsResponseDto> cartItemsResponseDtoList = cartItemsService.getAllCartItems();

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());


        log.debug("CartItemsController:getAllCartItems EXECUTION ENDED. traceId: {} response : {}", TraceIdHolder.getTraceId(), cartItemsResponseDtoList);
        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder().data(cartItemsResponseDtoList).status(StatusType.SUCCESS).build());

    }
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cart-items found", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CartItemsResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Cart-items could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Get a cart-item by id", description = "Get a cart-item by id")
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse> getCartItemsById(@PathVariable final UUID id) {

        log.debug("CartItemsController:getCartItemsById EXECUTION STARTED. traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        CartItemsResponseDto cartItemsResponseDto = cartItemsService.getCartItemsById(id);
        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("CartItemsController:getCartItemsById EXECUTION ENDED. traceId: {} response : {}", TraceIdHolder.getTraceId(), cartItemsResponseDto);

        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder()
                        .data(cartItemsResponseDto)
                        .status(StatusType.SUCCESS)
                        .build());
    }


    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cart-item updated", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CartItemsUpdateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Cart-item could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Update a cart-item by id", description = "Update a cart-item")
    @PutMapping("update/{id}")
    public ResponseEntity<SuccessResponse> updateCartItems(@PathVariable UUID id, @RequestBody CartItemsUpdateRequestDto cartItemsUpdateRequestDto) {

        log.debug("CartItemsController:updateCartItems EXECUTION STARTED. traceId: {} request id: {} payload: {}", TraceIdHolder.getTraceId(), id, cartItemsUpdateRequestDto);

        CartItemsUpdateResponseDto cartItemsUpdateResponseDto  = cartItemsService.updateCartItems(id,cartItemsUpdateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("CartItemsController:updateCartItems EXECUTION ENDED. traceId: {} response: {}", TraceIdHolder.getTraceId(), cartItemsUpdateRequestDto);
        return ResponseEntity.ok()
                .headers(headers)
                .body(SuccessResponse.builder()
                        .data(cartItemsUpdateRequestDto)
                        .status(StatusType.SUCCESS)
                        .build());

    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cart-item deleted."),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Cart-item could not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @Operation(summary = "Delete a cart-item by id", description = "Delete a Cart-item")
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteSubCategoryById(@PathVariable final UUID id) {

        log.debug("CartItemsController:deleteCartItemsById EXECUTION STARTED. traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        cartItemsService.deleteCartItemsById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("CartItemsController:deleteCartItemsById EXECUTION ENDED. traceId: {}", TraceIdHolder.getTraceId());
        return ResponseEntity.ok()
                .headers(headers)
                .build();

    }

}
