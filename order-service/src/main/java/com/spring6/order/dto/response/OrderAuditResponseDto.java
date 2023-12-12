package com.pm.spring.ema.order.dto.response;


import com.pm.spring.ema.common.enums.BrandStatusEnum;
import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.RevisionType;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class OrderAuditResponseDto {
    private UUID id;
    private String name;
    private String imageName;
    private BrandStatusEnum status;
    private String createdOn;
    private String lastUpdatedOn;
    private UUID subcategoryId;

    private Number revision;
    private RevisionType revisionType;

}
