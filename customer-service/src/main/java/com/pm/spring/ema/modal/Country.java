package com.pm.spring.ema.modal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Country {

    @Id
    @Column(nullable = false)
    private String code;

    @Column(nullable = false, unique = true)
    private String name;
}
