package com.pm.spring.ema.order.controller;

import com.pm.spring.ema.common.exeption.ErrorListResponse;
import com.pm.spring.ema.common.exeption.ErrorResponse;
import com.pm.spring.ema.common.util.FileUploadUtils;
import com.pm.spring.ema.common.utils.GlobalConstants;
import com.pm.spring.ema.common.utils.HttpStatusCodes;
import com.pm.spring.ema.order.dto.enums.OrderSearchKeyword;
import com.pm.spring.ema.order.dto.request.OrderCreateRequestDto;
import com.pm.spring.ema.order.dto.request.OrderUpdateRequestDto;
import com.pm.spring.ema.order.dto.response.OrderCreateResponseDto;
import com.pm.spring.ema.order.dto.response.OrderResponseDto;
import com.pm.spring.ema.order.dto.response.OrderUpdateResponseDto;
import com.pm.spring.ema.order.service.OrderService;
import com.pm.spring.ema.order.utils.TraceIdHolder;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("api/order")
public class OrderController {

    private final OrderService orderService;

    @Operation(tags = "Order", summary = "Create Order", description = "Create a new Order by entering order details")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Order", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping(value = "create")
    public ResponseEntity<OrderCreateResponseDto> createOrder(@RequestBody @Valid final OrderCreateRequestDto orderCreateRequestDto) {

        log.info("OrderController:createOrder execution started.");
        log.debug("OrderController:createOrder traceId: {} request payload: {}", TraceIdHolder.getTraceId(), orderCreateRequestDto);

        OrderCreateResponseDto savedOrderDto = orderService.create(orderCreateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.debug("OrderController:createOrder traceId: {} response: {}", TraceIdHolder.getTraceId(), savedOrderDto);
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
        log.info("OrderController:getOrderById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        OrderResponseDto orderFindResponseDto = orderService.getById(id);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("OrderController:getOrderById traceId: {} response : {}", TraceIdHolder.getTraceId(), orderFindResponseDto);
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
        log.info("OrderController:getAllOrders traceId: {}", TraceIdHolder.getTraceId());

        List<OrderResponseDto> orderFindResponseDtoList = orderService.getAll();

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("OrderController:getAllOrders traceId: {} response: {}", TraceIdHolder.getTraceId(), orderFindResponseDtoList);
        log.info("OrderController:getAllOrders execution ended.");

        return ResponseEntity.ok().headers(headers).body(orderFindResponseDtoList);

    }

    @Operation(tags = "Order", summary = "Update Order", description = "Update order by passing order id and order request body")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Order", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("update/{id}")
    public ResponseEntity<OrderUpdateResponseDto> updateOrder(@PathVariable UUID id, @RequestBody OrderUpdateRequestDto orderUpdateRequestDto) {

        log.info("OrderController:updateOrder started.");
        log.info("OrderController:updateOrder traceId: {} request id: {} payload: {}", TraceIdHolder.getTraceId(), id, orderUpdateRequestDto);

        OrderUpdateResponseDto orderUpdateResponseDto = orderService.update(id, orderUpdateRequestDto);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("OrderController:updateOrder traceId: {} response: {}", TraceIdHolder.getTraceId(), orderUpdateResponseDto);
        log.info("OrderController:updateOrder ended.");

        return ResponseEntity.ok().headers(headers).body(orderUpdateResponseDto);

    }

    @Operation(tags = "Order", summary = "Delete Order By Id", description = "Delete existing order by passing order id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = "Create a Order", content = {@Content(mediaType = "application/json", schema = @Schema(implementation = OrderCreateResponseDto.class))}),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = "Validation failed", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorListResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.CONFLICT, description = "Some data already exist", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = "Internal server error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteOrderById(@PathVariable final UUID id) {

        log.info("OrderController:deleteById started.");
        log.info("OrderController:deleteById traceId: {} request id: {}", TraceIdHolder.getTraceId(), id);

        orderService.deleteById(id);
        String dir = "../order-logos/" + id;
        FileUploadUtils.removeDir(dir);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("OrderController:deleteById traceId: {}", TraceIdHolder.getTraceId());
        log.info("OrderController:deleteById ended.");

        return ResponseEntity.noContent().headers(headers).build();

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
        log.info("OrderController:getOrdersByPage traceId: {} request pageNumber: {} perPageCount: {} sortField: {} sortDirection: {} searchField: {} searchKeyword: {}", TraceIdHolder.getTraceId(), pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        List<OrderResponseDto> orderFindResponseDtoList = orderService.getByPage(pageNumber, perPageCount, sortField, sortDirection, searchField, searchKeyword);

        HttpHeaders headers = new HttpHeaders();
        headers.add(GlobalConstants.TRACE_ID_HEADER, TraceIdHolder.getTraceId());

        log.info("OrderController:getOrdersByPage traceId: {} response: {}", TraceIdHolder.getTraceId(), orderFindResponseDtoList);
        log.info("OrderController:getOrdersByPage ended.");

        return ResponseEntity.ok().headers(headers).body(orderFindResponseDtoList);

    }

}
