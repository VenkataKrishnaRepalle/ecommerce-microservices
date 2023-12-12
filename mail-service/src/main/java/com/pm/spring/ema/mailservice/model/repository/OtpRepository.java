package com.pm.spring.ema.mailservice.model.repository;

import com.pm.spring.ema.mailservice.model.entity.Otp;
import com.pm.spring.ema.mailservice.model.enums.OtpStatus;
import com.pm.spring.ema.mailservice.model.enums.OtpType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OtpRepository extends JpaRepository<Otp, UUID> {

    Optional<Otp> findByUserUuidAndStatusAndType(UUID userId, OtpStatus otpStatus, OtpType type);

    List<Otp> getAllByUserUuidAndStatusAndType(UUID userId, OtpStatus otpStatus, OtpType type);

    List<Otp> getAllByStatusAndCreatedTimeBefore(OtpStatus otpStatus, Date date);
}
