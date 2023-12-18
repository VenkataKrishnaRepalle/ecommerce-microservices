package com.pm.spring.ema.category.model.entity;

import com.pm.spring.ema.category.common.enums.CategoryEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Category {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(length = 128, nullable = false, unique = true)
    private String name;

    @Column(length = 64, nullable = false, unique = true)
    private String alias;

    @Column(length = 128, nullable = false)
    private String imageName;

    @Column(name = "status")
    private CategoryEnum status;

    @CreationTimestamp
    private Instant createdOn;

    @UpdateTimestamp
    private Instant lastUpdatedOn;

    @OneToMany(mappedBy = "category", cascade = CascadeType.REMOVE)
    private Set<SubCategory> subCategories = new HashSet<>();


}
