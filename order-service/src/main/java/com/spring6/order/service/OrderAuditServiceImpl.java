package com.spring6.order.service;

import com.spring6.order.dto.mapper.OrderMapper;
import com.spring6.order.dto.response.OrderAuditResponseDto;
import com.spring6.order.model.dao.AuditDao;
import com.spring6.order.model.entity.Order;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderAuditServiceImpl implements OrderAuditService {

    private final AuditDao brandAuditDao;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderAuditResponseDto> getAuditRecords(UUID entityId) {

        List<Object[]> auditEntities = brandAuditDao.getAuditRecords(Order.class, entityId);
        List<OrderAuditResponseDto> auditDTOs = new ArrayList<>();

        for (Object[] auditEntity : auditEntities) {
            Order order = (Order) auditEntity[0];
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) auditEntity[1];
            RevisionType revisionType = (RevisionType) auditEntity[2];

            OrderAuditResponseDto auditDTO = orderMapper.orderToOrderAuditResponseDto(order);
            auditDTO.setRevision(revisionEntity.getId());
            auditDTO.setRevisionType(revisionType);

            auditDTOs.add(auditDTO);


        }
        return auditDTOs;
    }
}
