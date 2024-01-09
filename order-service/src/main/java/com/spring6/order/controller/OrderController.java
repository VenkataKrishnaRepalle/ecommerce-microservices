package com.spring6.order.controller;

import brave.Tracer;
import com.pm.spring.ema.common.util.GlobalConstants;
import com.pm.spring.ema.common.util.HttpStatusCodes;
import com.pm.spring.ema.common.util.api.ErrorResponse;
import com.pm.spring.ema.common.util.exception.ErrorListResponse;
import com.pm.spring.ema.order.dto.enums.OrderSearchKeyword;
import com.spring6.order.dto.request.OrderCreateRequestDto;
import com.spring6.order.dto.response.OrderCreateResponseDto;
import com.spring6.order.dto.response.OrderResponseDto;
import com.spring6.order.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    private final Tracer tracer;

    @Operation(tags = "Order", summary = "Create Order", description = "Create a new Order by entering order details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Order", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "create-order/{userId}")
    public ResponseEntity<OrderCreateResponseDto> createOrder(@RequestBody OrderCreateRequestDto orderCreateRequestDto, @PathVariable UUID userId) {

        log.info("OrderController:createOrder execution started.");

        var savedOrderDto = orderService.createOrder(userId, orderCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());

        log.info("OrderController:createOrder execution ended.");

        return ResponseEntity.status(HttpStatus.CREATED).headers(headers).body(savedOrderDto);
    }

    @Operation(tags = "Order", summary = "Get Order By Id", description = "Get Order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get Order Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("{id}")
    public ResponseEntity<OrderResponseDto> getOrderById(@PathVariable @Valid final UUID id) {

        log.info("OrderController:getOrderById execution started.");

        var orderFindResponseDto = orderService.getById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());

        log.info("OrderController:getOrderById execution ended.");

        return ResponseEntity.ok().headers(headers).body(orderFindResponseDto);

    }

    @Operation(tags = "Order", summary = "Get All Orders", description = "Get all orders by passing order id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Get Order Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("list")
    public ResponseEntity<List<OrderResponseDto>> getAllOrders() {

        log.info("OrderController:getAllOrders started.");

        var orderFindResponseDtoList = orderService.getAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());

        log.info("OrderController:getAllOrders execution ended.");

        return ResponseEntity.ok().headers(headers).body(orderFindResponseDtoList);

    }

    @Operation(tags = "Order", summary = "Cancel Order By Id", description = "Cancel Order by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cancel Order Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("cancel-order-by-orderId/{orderId}")
    public ResponseEntity cancelOrderById(@PathVariable UUID orderId) {
        orderService.cancelOrderById(orderId);
        return ResponseEntity.ok()
                .build();
    }

    @Operation(tags = "Order", summary = "Cancel Order By OrderDetailsId", description = "Cancel Order by OrderDetailsId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Cancel Order by OrderDetailsId Response", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.NOT_FOUND, description = "Order not found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("cancel-order/{orderId}/partial/{orderDetailId}")
    public ResponseEntity cancelOrderPartiallyByOrderDetailsId(@PathVariable UUID orderId, @PathVariable UUID orderDetailId) {
        orderService.cancelOrderPartiallyByOrderDetailsId(orderId, orderDetailId);
        return ResponseEntity.ok()
                .build();
    }

    @Operation(tags = "Order", summary = "Get Orders By Pagination", description = "Get orders by pagination by passing pagination attributes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = "Orders", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("page")
    public ResponseEntity<List<OrderResponseDto>> getOrdersByPage(@RequestParam(name = "pageNumber") Integer pageNumber,
                                                                  @RequestParam("perPageCount") Integer perPageCount,
                                                                  @RequestParam("sortField") String sortField,
                                                                  @RequestParam("sortDirection") String sortDirection,
                                                                  @RequestParam("searchField") OrderSearchKeyword searchField,
                                                                  @RequestParam("searchKeyword") String searchKeyword) {


        log.info("OrderController:getOrdersByPage started.");

        var orderFindResponseDtoList = orderService.getByPage(pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, tracer.currentSpan().context().traceIdString());

        log.info("OrderController:getOrdersByPage ended.");

        return ResponseEntity.ok().headers(headers).body(orderFindResponseDtoList);

    }

}
