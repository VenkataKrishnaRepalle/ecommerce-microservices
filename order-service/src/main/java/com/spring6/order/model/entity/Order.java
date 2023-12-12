package com.pm.spring.ema.order.model.entity;

import com.pm.spring.ema.order.model.enums.OrderStatus;
import com.pm.spring.ema.order.model.enums.PaymentMethod;
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
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
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

    private Instant orderDate;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    private BigDecimal productCostTotal; // original price of all products total

    private BigDecimal shippingCost; // shipping cost of order
    private BigDecimal tax;

    private BigDecimal subtotal;

    private BigDecimal total;// shippingCost + subtotal + tax

    private String recipientAddress;

    private Date estimatedDeliveryDate;

    private int estimatedDeliveryDays;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    @Column(length = 15, nullable = false)
    private OrderStatus status;

    //    @ManyToOne
//    @JoinColumn(name = "customer_id")
//    private Customer customer;
    @Column(nullable = false, length = 36)
    private UUID customerId;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private Set<OrderDetail> orderDetails = new HashSet<>();


}


