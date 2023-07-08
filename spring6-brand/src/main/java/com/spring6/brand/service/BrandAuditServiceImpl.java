package com.spring6.brand.service;

import com.spring6.brand.dto.BrandAuditResponseDto;
import com.spring6.brand.entity.Brand;
import com.spring6.brand.mapper.BrandMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BrandAuditServiceImpl implements BrandAuditService {

    @PersistenceContext
    private EntityManager entityManager;
    private final BrandMapper brandMapper;

    @Override
    public List<BrandAuditResponseDto> getAuditRecords(UUID entityId) {

        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        List<Object[]> auditEntities = auditReader.createQuery()
                .forRevisionsOfEntity(Brand.class, false, true)
                .add(AuditEntity.id().eq(entityId))
                .getResultList();

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
