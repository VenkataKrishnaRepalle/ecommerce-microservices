package com.spring6.order.service.impl;

import com.pm.spring.ema.common.util.dto.ProductFindResponseDto;
import com.pm.spring.ema.order.dto.enums.OrderSearchKeyword;
import com.pm.spring.ema.common.util.exception.ErrorCodes;
import com.spring6.order.dto.mapper.OrderDetailsMapper;
import com.spring6.order.dto.mapper.OrderMapper;
import com.spring6.order.dto.request.OrderCreateRequestDto;
import com.spring6.order.dto.request.OrderUpdateRequestDto;
import com.spring6.order.dto.response.OrderCreateResponseDto;
import com.spring6.order.dto.response.OrderResponseDto;
import com.spring6.order.dto.response.OrderUpdateResponseDto;
import com.spring6.order.exception.InvalidInputException;
import com.spring6.order.exception.OrderNotFoundException;
import com.spring6.order.exception.OrderQuantityException;
import com.spring6.order.model.entity.Order;
import com.spring6.order.model.enums.OrderStatus;
import com.spring6.order.model.repository.OrderDetailRepository;
import com.spring6.order.model.repository.OrderRepository;
import com.spring6.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final OrderDetailRepository orderDetailRepository;

    private final OrderMapper orderMapper;

    private final OrderDetailsMapper orderDetailsMapper;

    private final WebClient webClient;

    public List<OrderResponseDto> getAll() {
        log.info("OrderService:getAllOrders execution started.");

        var orderFindResponseDtoList = orderRepository.findAll()
                .stream()
                .map(orderMapper::orderToOrderResponseDto)
                .toList();

        log.info("OrderService:getAllOrders execution ended.");

        return orderFindResponseDtoList;
    }

    public List<OrderResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDirectory, OrderSearchKeyword searchField, String searchKeyword) {
        log.info("OrderService:getOrdersByPage execution started.");

        if (sortField.isBlank()) {
            sortField = "name";

        }
        Sort sort = Sort.by(sortField);
        sort = sortDirectory.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);

        Page<Order> orderList;

        orderList = orderRepository.findAll(pageable);

        var orderFindResponseDtoList = orderList.stream()
                .map(orderMapper::orderToOrderResponseDto)
                .toList();

        log.info("OrderService:getOrdersByPage execution ended.");

        return orderFindResponseDtoList;

    }

    @Override
    public OrderResponseDto getById(UUID id) throws OrderNotFoundException {
        log.info("OrderService:getOrderById execution started.");

        var optionalOrder = orderRepository.findById(id);

        if (optionalOrder.isEmpty()) {
            log.info("OrderService:getOrderById execution ended.");
            throw new OrderNotFoundException(ErrorCodes.E0501, id.toString());
        }

        var orderFindResponseDto = orderMapper.orderToOrderResponseDto(optionalOrder.get());

        log.info("OrderService:getOrderById execution ended.");

        return orderFindResponseDto;
    }

    @Override
    public OrderCreateResponseDto createOrder(UUID userId, OrderCreateRequestDto orderCreateRequestDto) {
        var orderDetails = orderCreateRequestDto.getOrderDetails()
                .stream()
                .map(orderDetailsMapper::orderDetailCreateRequestDtoToOrderDetail)
                .toList();

        for (var orderDetail : orderDetails) {
            var productId = orderDetail.getProductId();

            var isProductExists = webClient.get()
                    .uri("isProductExistsById/" + userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            if (Boolean.FALSE.equals(isProductExists)) {
                throw new InvalidInputException(ErrorCodes.E2003 + productId);
            }

            var product = webClient.get()
                    .uri(String.valueOf(productId))
                    .retrieve()
                    .bodyToMono(ProductFindResponseDto.class)
                    .block();

            if (orderDetail.getQuantity() <= 0) {
                throw new OrderQuantityException(ErrorCodes.E3001 + product.getName() + " " + product.getAlias());
            }

            var subTotal = BigDecimal.valueOf(product.getPrice()).multiply(BigDecimal.valueOf(orderDetail.getQuantity()));
            orderDetail.setUnitPrice(BigDecimal.valueOf(product.getPrice()));
            orderDetail.setShippingCost(BigDecimal.ZERO);
            orderDetail.setProductCost(BigDecimal.valueOf(product.getPrice()));
            orderDetail.setSubtotal(subTotal);
            orderDetail.setStatus(OrderStatus.NEW);
        }

        var cost = orderDetails.stream()
                .mapToDouble(orderDetail -> orderDetail.getSubtotal().doubleValue())
                .sum();


        var order = Order.builder()
                .userId(userId)
                .orderDetails(orderDetails)
                .productTotalCost(BigDecimal.valueOf(cost))
                .tax(BigDecimal.ZERO)
                .shippingCost(BigDecimal.ZERO)
                .total(BigDecimal.valueOf(cost))
                .status(OrderStatus.NEW)
                .build();

        var savedOrder = orderRepository.save(order);

        for (var orderDetail : orderDetails) {
            orderDetail.setOrder(savedOrder);
        }

        orderDetailRepository.saveAll(orderDetails);

        return orderMapper.orderToOrderCreateResponseDto(savedOrder);
    }

    @Override
    public void cancelOrderById(UUID orderId) {
        var optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException(ErrorCodes.E3002, orderId.toString());
        }

        var order = optionalOrder.get();
        var orderDetails = order.getOrderDetails();

        for (var orderDetail : orderDetails) {
            orderDetail.setStatus(OrderStatus.CANCELLED);
        }
        order.setStatus(OrderStatus.CANCELLED);

        orderDetailRepository.saveAll(orderDetails);
        orderRepository.save(order);
    }

    @Override
    public void cancelOrderPartiallyByOrderDetailsId(UUID orderId, UUID orderDetailId) {
        var optionalOrderDetail = orderDetailRepository.findById(orderDetailId);

        if (optionalOrderDetail.isEmpty()) {
            throw new OrderNotFoundException(ErrorCodes.E3002, orderDetailId.toString());
        }

        var orderDetail = optionalOrderDetail.get();
        if (Boolean.FALSE.equals(orderDetail.getOrder().getId().equals(orderId))) {
            throw new InvalidInputException(ErrorCodes.E3003);
        }

        orderDetail.setStatus(OrderStatus.CANCELLED);
        orderDetailRepository.save(orderDetail);
    }

}
