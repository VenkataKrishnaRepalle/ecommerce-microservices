package com.pm.spring.ema.auth.service.common.model.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.hibernate.envers.query.AuditEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class AuditDaoImpl implements AuditDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Object[]> getAuditRecords(Class<?> entityClass, UUID entityId) {
        AuditReader auditReader = AuditReaderFactory.get(entityManager);

        return auditReader.createQuery()
                .forRevisionsOfEntity(entityClass, false, true)
                .addOrder(AuditEntity.revisionNumber().asc())
                .add(AuditEntity.id().eq(entityId))
//                .add(AuditEntity.revisionType().eq(RevisionType.DEL))
                .getResultList();

    }
}
