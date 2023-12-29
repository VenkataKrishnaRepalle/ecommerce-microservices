package com.spring6.order.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
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

    private Date estimatedDeliveryDate;

    private int estimatedDeliveryDays;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal shippingCost;

    private BigDecimal productCost;

    private BigDecimal subtotal; // unitPrice x quantity

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonBackReference
    private Order order;
}
