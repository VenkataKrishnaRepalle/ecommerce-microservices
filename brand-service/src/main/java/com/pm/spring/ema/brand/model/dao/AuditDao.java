package com.pm.spring.ema.brand.model.dao;

import java.util.List;
import java.util.UUID;

public interface AuditDao {
  List<Object[]> getAuditRecords(Class<?> entityClass, UUID entityId);
}
