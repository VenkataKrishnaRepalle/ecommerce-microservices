package com.spring6.entity;

import com.spring6.enums.EnabledStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @JdbcTypeCode(SqlTypes.CHAR)
    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Column(length = 45, nullable = false)
    private String email;

    @Column(length = 64, nullable = false)
    private String password;

    @Column(length = 45, nullable = false)
    private String firstName;

    @Column(length = 45, nullable = false)
    private String lastName;

    @Column(length = 15, nullable = false)
    private String phoneNumber;

    @Column(length = 64, nullable = false)
    private String addressLine1;

    @Column(length = 64)
    private String addressLine2;

    @Column(length = 45, nullable = false)
    private String city;

    @Column(length = 45, nullable = false)
    private String state;

    @Column(nullable = false)
    private String country;

    @Column(length = 10, nullable = false)
    private String postalCode;

    @Enumerated
    private EnabledStatus isEnabled;

    @Column(length = 64, name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "otp_requested_time")
    private Date otpRequestedTime;

    @CreationTimestamp
    @Column(name = "creation_time", updatable = false)
    private Date createdTime;

    @UpdateTimestamp
    @Column(name = "updation_time")
    private Date updatedTime;

//    @JdbcTypeCode(SqlTypes.VARCHAR)
//    @Column(columnDefinition = "varchar(36)", updatable = false, nullable = false)
//    private UUID userId;
}
