package com.pm.spring.ema.auth.service;

import com.pm.spring.ema.auth.dto.mapper.AccountMapper;
import com.pm.spring.ema.auth.dto.response.UserAuditResponseDto;
import com.pm.spring.ema.auth.model.dao.AuditDao;
import com.pm.spring.ema.auth.model.entity.Account;
import lombok.RequiredArgsConstructor;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionType;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountAuditServiceImpl implements AccountAuditService {

    private final AuditDao brandAuditDao;
    private final AccountMapper accountMapper;

    @Override
    public List<UserAuditResponseDto> getAuditRecords(UUID entityId) {

        List<Object[]> auditEntities =  brandAuditDao.getAuditRecords(Account.class, entityId);
        List<UserAuditResponseDto> auditDTOs = new ArrayList<>();

        for (Object[] auditEntity : auditEntities) {
            Account account = (Account) auditEntity[0];
            DefaultRevisionEntity revisionEntity = (DefaultRevisionEntity) auditEntity[1];
            RevisionType revisionType = (RevisionType) auditEntity[2];

            UserAuditResponseDto auditDTO = accountMapper.userToUserAuditResponseDto(account);
            auditDTO.setRevision(revisionEntity.getId());
            auditDTO.setRevisionType(revisionType);

            auditDTOs.add(auditDTO);


        }
        return auditDTOs;
    }
}
