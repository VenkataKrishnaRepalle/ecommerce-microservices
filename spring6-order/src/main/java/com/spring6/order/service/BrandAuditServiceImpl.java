package com.spring6.order.service;

import com.spring6.order.dto.response.BrandAuditResponseDto;
import com.spring6.order.model.dao.AuditDao;
import com.spring6.order.model.entity.Order;
import com.spring6.order.dto.mapper.BrandMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BrandAuditServiceImpl implements BrandAuditService {

    private final AuditDao brandAuditDao;
    private final BrandMapper brandMapper;

    @Override
    public List<BrandAuditResponseDto> getAuditRecords(UUID entityId) {

        List<Object[]> auditEntities =  brandAuditDao.getAuditRecords(Order.class, entityId);
        List<BrandAuditResponseDto> auditDTOs = new ArrayList<>();

        for (Object[] auditEntity : auditEntities) {
            Order order = (Order) auditEntity[0];
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) auditEntity[1];
            RevisionType revisionType = (RevisionType) auditEntity[2];

            BrandAuditResponseDto auditDTO = brandMapper.brandToBrandAuditResponseDto(order);
            auditDTO.setRevision(revisionEntity.getId());
            auditDTO.setRevisionType(revisionType);

            auditDTOs.add(auditDTO);


        }
        return auditDTOs;
    }
}
