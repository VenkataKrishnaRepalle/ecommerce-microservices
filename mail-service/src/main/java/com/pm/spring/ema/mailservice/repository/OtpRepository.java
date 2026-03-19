package com.pm.spring.ema.mailservice.repository;

import com.pm.spring.ema.mailservice.model.Otp;
import com.pm.spring.ema.mailservice.model.OtpStatus;
import com.pm.spring.ema.mailservice.model.OtpType;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpRepository extends JpaRepository<Otp, UUID> {

  Optional<Otp> findByUserUuidAndStatusAndType(UUID userId, OtpStatus otpStatus, OtpType type);

  List<Otp> getAllByUserUuidAndStatusAndType(UUID userId, OtpStatus otpStatus, OtpType type);

  List<Otp> getAllByStatusAndCreatedTimeBefore(OtpStatus otpStatus, Date date);
}
