package com.spring6.order.dto.response;


import com.spring6.order.model.enums.OrderStatus;
import lombok.Builder;
import lombok.Data;
import org.hibernate.envers.RevisionType;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class OrderAuditResponseDto {
    private UUID id;
    private String name;
    private String imageName;
    private OrderStatus status;
    private String createdOn;
    private String lastUpdatedOn;
    private UUID subcategoryId;

    private Number revision;
    private RevisionType revisionType;

}
