package com.spring6.order.service;

import com.spring6.order.dto.request.OrderCreateRequestDto;
import com.spring6.order.dto.response.OrderCreateResponseDto;
import com.spring6.order.dto.request.OrderUpdateRequestDto;
import com.spring6.order.dto.response.OrderResponseDto;
import com.spring6.order.dto.response.OrderUpdateResponseDto;
import com.spring6.order.model.dao.OrderDao;
import com.spring6.order.model.entity.Order;
import com.spring6.order.dto.enums.OrderSearchKeyword;
import com.spring6.order.exception.OrderNotFoundException;
import com.spring6.order.dto.mapper.OrderMapper;
import com.spring6.order.utils.TraceIdHolder;
import com.spring6.common.exeption.ErrorCodes;
import com.spring6.common.exeption.ErrorMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderDao orderDao;
    private final OrderMapper orderMapper;

    public List<OrderResponseDto> getAll() {
        log.info("OrderService:getAllOrders execution started.");
        log.debug("OrderService:getAllOrders traceId: {}", TraceIdHolder.getTraceId());

        List<OrderResponseDto> orderFindResponseDtoList = orderDao.findAll()
                .stream()
                .map(orderMapper::orderToOrderResponseDto)
                .toList();

        log.debug("OrderService:getAllOrders traceId: {}, response {} ", TraceIdHolder.getTraceId(), orderFindResponseDtoList);
        log.info("OrderService:getAllOrders execution ended.");

        return orderFindResponseDtoList;
    }

    public List<OrderResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDirectory, OrderSearchKeyword searchField, String searchKeyword) {
        log.info("OrderService:getOrdersByPage execution started.");
        log.debug("OrderService:getOrdersByPage traceId: {},  pageNumber: {}, perPageCount: {}, sortField: {}, sortDirectory: {}, searchField: {}, searchKeyword: {}", TraceIdHolder.getTraceId(), pageNumber, perPageCount, sortField, sortDirectory, searchField, searchKeyword);

        if (sortField.isBlank()) {
            sortField = "name";

        }
        Sort sort = Sort.by(sortField);
        sort = sortDirectory.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);

        Page<Order> orderList;

//        if (searchKeyword != null && searchField.equals(OrderSearchKeyword.BRAND_NAME)) {
//            orderList =  orderDao.findAllByName(searchKeyword, pageable);
//
//        } else {
//            orderList =  orderDao.findAll(pageable);
//        }

        orderList =  orderDao.findAll(pageable);

        List<OrderResponseDto> orderFindResponseDtoList = orderList.stream()
                .map(orderMapper::orderToOrderResponseDto)
                .toList();

        log.debug("OrderService:getOrdersByPage traceId: {}", TraceIdHolder.getTraceId());
        log.info("OrderService:getOrdersByPage execution ended.");

        return orderFindResponseDtoList;

    }

    @Override
    public OrderResponseDto getById(UUID id) throws OrderNotFoundException {
        log.info("OrderService:getOrderById execution started.");
        log.debug("OrderService:getOrderById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Order> optionalOrder = orderDao.findById(id);

        if (!optionalOrder.isPresent()) {
            log.error("OrderService:getOrderById traceId: {}, errorMessage: Order Not found", TraceIdHolder.getTraceId());
            log.info("OrderService:getOrderById execution ended.");
            throw new OrderNotFoundException(ErrorCodes.E0501, id.toString());
        }

        OrderResponseDto orderFindResponseDto = orderMapper.orderToOrderResponseDto(optionalOrder.get());

        log.debug("OrderService:getOrderById traceId: {}, response: {}", TraceIdHolder.getTraceId(), orderFindResponseDto);
        log.info("OrderService:getOrderById execution ended.");

        return orderFindResponseDto;
    }

    @Override
    public OrderCreateResponseDto create(OrderCreateRequestDto orderCreateRequestDto) {
        log.info("OrderService:createOrder execution started.");
        log.debug("OrderService:createOrder traceId: {} , orderCreateRequestDto: {}", TraceIdHolder.getTraceId(), orderCreateRequestDto);

        Order order = orderMapper.orderCreateRequestDtoToOrder(orderCreateRequestDto);
        Order orderCreated = orderDao.save(order);
        OrderCreateResponseDto orderCreateResponseDto = orderMapper.orderToOrderCreateResponseDto(orderCreated);

        log.debug("OrderService:createOrder traceId: {}, response: {}", TraceIdHolder.getTraceId(), orderCreateResponseDto);
        log.info("OrderService:createOrder execution ended.");

        return orderCreateResponseDto;

    }

    @Override
    public OrderUpdateResponseDto update(final UUID id, OrderUpdateRequestDto orderUpdateRequestDto)
            throws OrderNotFoundException {
        log.info("OrderService:updateOrder execution started.");
        log.debug("OrderService:updateOrder traceId: {}, id: {}, orderCreateRequestDto: {}", TraceIdHolder.getTraceId(), id, orderUpdateRequestDto);


        Optional<Order> optionalOrder = orderDao.findById(id);

        if (!optionalOrder.isPresent()) {
            throw new OrderNotFoundException(ErrorCodes.E0502, id.toString());
        }

        Order order = orderMapper.orderUpdateRequestDtoToOrder(orderUpdateRequestDto);
        order.setId(optionalOrder.get().getId());

        Order orderUpdated = orderDao.save(order);
        OrderUpdateResponseDto orderUpdateResponseDto = orderMapper.orderToOrderUpdateResponseDto(orderUpdated);

        log.debug("OrderService:updateOrder traceId: {}, response: {}", TraceIdHolder.getTraceId(), orderUpdateResponseDto);
        log.info("OrderService:updateOrder execution ended.");

        return orderUpdateResponseDto;
    }

    @Override
    public void deleteById(UUID id) throws OrderNotFoundException {
        log.info("OrderService:deleteOrderById execution started.");
        log.debug("OrderService:deleteOrderById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Long orderCountById = orderDao.countById(id);
        if (orderCountById == 0) {
            log.error("OrderService:deleteOrderById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E0503, id.toString()));
            throw new OrderNotFoundException(ErrorCodes.E0503, id.toString());
        }

        orderDao.deleteById(id);
        log.info("OrderService:deleteOrderById execution ended.");

    }

}
