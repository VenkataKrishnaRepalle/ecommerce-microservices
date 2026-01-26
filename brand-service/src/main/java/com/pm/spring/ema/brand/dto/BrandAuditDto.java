package com.pm.spring.ema.brand.dto;


import com.pm.spring.ema.common.util.enums.BrandStatusEnum;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.RevisionType;

import java.util.UUID;

@Data
@Builder
public class BrandAuditDto {
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
