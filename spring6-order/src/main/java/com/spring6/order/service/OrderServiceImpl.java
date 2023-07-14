package com.spring6.order.service;

import com.spring6.order.dto.request.OrderCreateRequestDto;
import com.spring6.order.dto.response.OrderCreateResponseDto;
import com.spring6.order.dto.request.OrderUpdateRequestDto;
import com.spring6.order.dto.response.OrderUpdateResponseDto;
import com.spring6.order.model.dao.OrderDao;
import com.spring6.order.model.entity.Order;
import com.spring6.order.dto.enums.OrderSearchKeywordEnum;
import com.spring6.order.exception.OrderNotFoundException;
import com.spring6.order.dto.mapper.OrderMapper;
import com.spring6.order.utils.TraceIdHolder;
import com.spring6.common.dto.BrandFindResponseDto;
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

    public List<BrandFindResponseDto> getAll() {
        log.info("BrandService:getAllBrands execution started.");
        log.debug("BrandService:getAllBrands traceId: {}", TraceIdHolder.getTraceId());

        List<BrandFindResponseDto> brandFindResponseDtoList = orderDao.findAll()
                .stream()
                .map(orderMapper::brandToBrandFindResponseDto)
                .toList();

        log.debug("BrandService:getAllBrands traceId: {}, response {} ", TraceIdHolder.getTraceId(), brandFindResponseDtoList);
        log.info("BrandService:getAllBrands execution ended.");

        return brandFindResponseDtoList;
    }

    public List<BrandFindResponseDto> getByPage(Integer pageNumber, Integer perPageCount, String sortField, String sortDirectory, OrderSearchKeywordEnum searchField, String searchKeyword) {
        log.info("BrandService:getBrandsByPage execution started.");
        log.debug("BrandService:getBrandsByPage traceId: {},  pageNumber: {}, perPageCount: {}, sortField: {}, sortDirectory: {}, searchField: {}, searchKeyword: {}", TraceIdHolder.getTraceId(), pageNumber, perPageCount, sortField, sortDirectory, searchField, searchKeyword);

        if (sortField.isBlank()) {
            sortField = "name";

        }
        Sort sort = Sort.by(sortField);
        sort = sortDirectory.equals("ASC") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNumber - 1, perPageCount, sort);

        Page<Order> brandList;

        if (searchKeyword != null && searchField.equals(OrderSearchKeywordEnum.BRAND_NAME)) {
            brandList =  orderDao.findAllByName(searchKeyword, pageable);

        } else {
            brandList =  orderDao.findAll(pageable);
        }

        List<BrandFindResponseDto> brandFindResponseDtoList = brandList.stream()
                .map(orderMapper::brandToBrandFindResponseDto)
                .toList();

        log.debug("BrandService:getBrandsByPage traceId: {}", TraceIdHolder.getTraceId());
        log.info("BrandService:getBrandsByPage execution ended.");

        return brandFindResponseDtoList;

    }

    @Override
    public BrandFindResponseDto getById(UUID id) throws OrderNotFoundException {
        log.info("BrandService:getBrandById execution started.");
        log.debug("BrandService:getBrandById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Order> optionalBrand = orderDao.findById(id);

        if (!optionalBrand.isPresent()) {
            log.error("BrandService:getBrandById traceId: {}, errorMessage: Brand Not found", TraceIdHolder.getTraceId());
            log.info("BrandService:getBrandById execution ended.");
            throw new OrderNotFoundException(ErrorCodes.E0501, id.toString());
        }

        BrandFindResponseDto brandFindResponseDto = orderMapper.brandToBrandFindResponseDto(optionalBrand.get());

        log.debug("BrandService:getBrandById traceId: {}, response: {}", TraceIdHolder.getTraceId(), brandFindResponseDto);
        log.info("BrandService:getBrandById execution ended.");

        return brandFindResponseDto;
    }

    @Override
    public OrderCreateResponseDto create(OrderCreateRequestDto orderCreateRequestDto) {
        log.info("BrandService:createBrand execution started.");
        log.debug("BrandService:createBrand traceId: {} , brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), orderCreateRequestDto);

        if (isNameExist(orderCreateRequestDto.getName())) {
            log.error("BrandService:createBrand traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E0506, orderCreateRequestDto.getName()));
            throw new BrandNameAlreadyExistException(ErrorCodes.E0506, orderCreateRequestDto.getName());
        }

        Order order = orderMapper.brandCreateRequestDtoToBrand(orderCreateRequestDto);
        Order orderCreated = orderDao.save(order);
        OrderCreateResponseDto orderCreateResponseDto = orderMapper.brandToBrandCreateResponseDto(orderCreated);

        log.debug("BrandService:createBrand traceId: {}, response: {}", TraceIdHolder.getTraceId(), orderCreateResponseDto);
        log.info("BrandService:createBrand execution ended.");

        return orderCreateResponseDto;

    }

    @Override
    public OrderUpdateResponseDto update(final UUID id, OrderUpdateRequestDto orderUpdateRequestDto)
            throws OrderNotFoundException {
        log.info("BrandService:updateBrand execution started.");
        log.debug("BrandService:updateBrand traceId: {}, id: {}, brandCreateRequestDto: {}", TraceIdHolder.getTraceId(), id, orderUpdateRequestDto);


        Optional<Order> optionalBrand = orderDao.findById(id);

        if (!optionalBrand.isPresent()) {
            throw new OrderNotFoundException(ErrorCodes.E0502, id.toString());
        }

        Order order = orderMapper.brandUpdateRequestDtoToBrand(orderUpdateRequestDto);
        order.setId(optionalBrand.get().getId());

        Order orderUpdated = orderDao.save(order);
        OrderUpdateResponseDto orderUpdateResponseDto = orderMapper.brandToBrandUpdateResponseDto(orderUpdated);

        log.debug("BrandService:updateBrand traceId: {}, response: {}", TraceIdHolder.getTraceId(), orderUpdateResponseDto);
        log.info("BrandService:updateBrand execution ended.");

        return orderUpdateResponseDto;
    }

    @Override
    public void deleteById(UUID id) throws OrderNotFoundException {
        log.info("BrandService:deleteBrandById execution started.");
        log.debug("BrandService:deleteBrandById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Long brandCountById = orderDao.countById(id);
        if (brandCountById == 0) {
            log.error("BrandService:deleteBrandById traceId: {}, errorMessage: {}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E0503, id.toString()));
            throw new OrderNotFoundException(ErrorCodes.E0503, id.toString());
        }

        orderDao.deleteById(id);
        log.info("BrandService:deleteBrandById execution ended.");

    }

    @Override
    public String updateImageName(UUID brandId, String fileName) {
        log.info("BrandService:updateImageName execution started.");
        log.debug("BrandService:updateImageName traceId: {}, brandId:{}, fileName: {}", TraceIdHolder.getTraceId(), brandId, fileName);

        Optional<Order> optionalBrand = orderDao.findById(brandId);

        if (!optionalBrand.isPresent()) {
            log.error("BrandService:updateImageName traceId: {}, errorMessage:{}", TraceIdHolder.getTraceId(), ErrorMessage.message(ErrorCodes.E0504, brandId.toString()));
            throw new OrderNotFoundException(ErrorCodes.E0504, brandId.toString());
        }

        Order order = optionalBrand.get();
        order.setImageName(fileName);

        Order orderUpdated = orderDao.save(order);

        log.debug("BrandService:updateImageName traceId: {}, updatedImageName: {}", TraceIdHolder.getTraceId(), orderUpdated.getImageName());
        log.info("BrandService:updateImageName execution ended.");

        return orderUpdated.getImageName();
    }

    @Override
    public String getImageNameById(UUID id) {
        log.info("BrandService:getBrandImageNameById execution started.");
        log.debug("BrandService:getBrandImageNameById traceId: {}, id: {}", TraceIdHolder.getTraceId(), id);

        Optional<Order> optionalBrand = orderDao.findById(id);

        if (!optionalBrand.isPresent()) {
            log.error("BrandService:getBrandImageNameById traceId: {}, errorMessage: Brand Not found", TraceIdHolder.getTraceId());
            log.info("BrandService:getBrandImageNameById execution ended.");
            throw new OrderNotFoundException(ErrorCodes.E0508, id.toString());
        }

        String imageName =  optionalBrand.get().getImageName();

        log.debug("BrandService:getBrandImageNameById traceId: {}, response: {}", TraceIdHolder.getTraceId(), imageName);
        log.info("BrandService:getBrandImageNameById execution ended.");

        return imageName;
    }

    @Override
    public Boolean isIdExist(UUID uuid) {
        log.info("BrandService:isIdExist execution started. traceId: {}", TraceIdHolder.getTraceId());
        log.debug("BrandService:isIdExist traceId: {}, id: {}", TraceIdHolder.getTraceId(), uuid);

        Optional<Order> optionalBrand = orderDao.findById(uuid);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

    private Boolean isNameExist(String name) {
        log.info("BrandService:isNameExist execution started. traceId: {}", TraceIdHolder.getTraceId());

        Optional<Order> optionalBrand = orderDao.findByName(name);
        if (optionalBrand.isPresent()) {
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }

}
