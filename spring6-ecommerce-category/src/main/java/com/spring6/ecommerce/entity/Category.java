package com.spring6.ecommerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
    @Column(length = 36,columnDefinition = "varchar",updatable = false,nullable = false)
    private UUID id;
    @Column(length =128,nullable = false,unique = true )
    private String name;
    @Column(length =64,nullable = false,unique = true )
    private String ailas;
    @Column(length =128,nullable = false)
    private String image;

    private boolean enabled;

    @OneToOne
    @JoinColumn(name = "parent_id")
    private Category parent;
    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();
}
