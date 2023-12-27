package com.spring.ema.shoppingcart.model.entity;

import com.spring.ema.shoppingcart.enums.ShoppingCartStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Data
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(name = "customer_id",unique = true, nullable = false)
    private UUID customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status",nullable = false)
    private ShoppingCartStatus status;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItems> cartItems = new ArrayList<>();

    @CreationTimestamp
    @Column(nullable = false, name = "created_time", updatable = false)
    private Date createdTime;



}
