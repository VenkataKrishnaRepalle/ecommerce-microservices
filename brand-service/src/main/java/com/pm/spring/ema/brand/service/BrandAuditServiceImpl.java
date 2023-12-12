package com.pm.spring.ema.brand.service;

import com.pm.spring.ema.brand.dto.response.BrandAuditResponseDto;
import com.pm.spring.ema.brand.model.dao.AuditDao;
import com.pm.spring.ema.brand.model.entity.Brand;
import com.pm.spring.ema.brand.mapper.BrandMapper;
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

        List<Object[]> auditEntities =  brandAuditDao.getAuditRecords(Brand.class, entityId);
        List<BrandAuditResponseDto> auditDTOs = new ArrayList<>();

        for (Object[] auditEntity : auditEntities) {
            Brand brand = (Brand) auditEntity[0];
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) auditEntity[1];
            RevisionType revisionType = (RevisionType) auditEntity[2];

            BrandAuditResponseDto auditDTO = brandMapper.brandToBrandAuditResponseDto(brand);
            auditDTO.setRevision(revisionEntity.getId());
            auditDTO.setRevisionType(revisionType);

            auditDTOs.add(auditDTO);


        }
        return auditDTOs;
    }
}
