package com.spring6.order.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
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
    private Long id;

    private int quantity;

    private BigDecimal unitPrice;

    private BigDecimal shippingCost;

    private BigDecimal productCost;

    private BigDecimal subtotal; // unitPrice x quantity


    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;

    private UUID productId;

}
