package com.spring6.order.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.spring6.order.model.enums.OrderStatus;
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

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Audited
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_detail", indexes = {@Index(name = "idx_order_detail_id", columnList = "id")})
@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID productId;

    @Column(nullable = false, length = 45)
    private String firstName;

    @Column(nullable = false, length = 45)
    private String lastName;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    @Column(length = 15)
    private String secondaryPhoneNumber;

    @Column(nullable = false, length = 64)
    private String addressLine1;

    @Column(length = 64)
    private String addressLine2;

    @Column(nullable = false, length = 45)
    private String city;

    @Column(nullable = false, length = 45)
    private String district;

    @Column(nullable = false, length = 45)
    private String state;

    @Column(nullable = false, length = 45)
    private String country;

    @Column(nullable = false, length = 10)
    private String postalCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @Temporal(TemporalType.DATE)
    private Date estimatedDeliveryDate;

    @Column(nullable = false)
    private int estimatedDeliveryDays;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private BigDecimal unitPrice;

    @Column(nullable = false)
    private BigDecimal shippingCost;

    @Column(nullable = false)
    private BigDecimal productCost;

    @Column(nullable = false)
    private BigDecimal subtotal; // unitPrice x quantity

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdTime;

    @UpdateTimestamp
    @Column(nullable = false)
    private Instant lastUpdatedTime;
}
