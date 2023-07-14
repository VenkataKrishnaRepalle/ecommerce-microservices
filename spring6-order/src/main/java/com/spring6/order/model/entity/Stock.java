package com.spring6.order.model.entity;

import com.spring6.order.model.enums.OrderStatusEnum;
import com.spring6.order.model.enums.StockStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order", indexes = {
        @Index(name = "idx_order_id", columnList = "id"),
        @Index(name = "idx_order_status", columnList = "status")
})
@Entity
public class Stock {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    private BigDecimal stock_quantity;
    private BigDecimal min_stock_threshold;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private StockStatusEnum status;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;


}


