package com.spring6.order.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pm.spring.ema.order.model.enums.PaymentMethod;
import com.spring6.order.model.enums.OrderStatus;
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
import java.util.*;

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

    private BigDecimal productCostTotal;

    private BigDecimal shippingCost;

    private BigDecimal tax;

    private BigDecimal subtotal;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false, length = 36)
    private UUID customerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("order")
    private List<OrderDetail> orderDetails;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant lastUpdatedTime;
}


