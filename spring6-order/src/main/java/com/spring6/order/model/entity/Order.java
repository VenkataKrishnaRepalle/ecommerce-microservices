package com.spring6.order.model.entity;

import com.spring6.common.enums.BrandStatusEnum;
import com.spring6.order.model.enums.OrderStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.envers.Audited;
import org.hibernate.type.SqlTypes;

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
public class Order {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;


    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private OrderStatusEnum status;

    private Instant orderDate;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

//    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;
    @Column(nullable = false, length = 36)
    private UUID customerId;

}


