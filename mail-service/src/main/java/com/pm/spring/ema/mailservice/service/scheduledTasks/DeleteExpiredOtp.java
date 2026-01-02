package com.pm.spring.ema.mailservice.service.scheduledTasks;

import com.pm.spring.ema.mailservice.model.OtpStatus;
import com.pm.spring.ema.mailservice.repository.OtpRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
@RequiredArgsConstructor
public class DeleteExpiredOtp {

    private final OtpRepository otpRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void deleteExpiredOtp() {
        var otpExpiredList = otpRepository.getAllByStatusAndCreatedTimeBefore(OtpStatus.EXPIRED,
                new java.util.Date(Calendar.getInstance().getTimeInMillis() - 24 * 60 * 60 * 1000));
        var otpUsedList = otpRepository.getAllByStatusAndCreatedTimeBefore(OtpStatus.USED,
                new java.util.Date(Calendar.getInstance().getTimeInMillis() - 24 * 60 * 60 * 1000));

        otpRepository.deleteAll(otpExpiredList);
        otpRepository.deleteAll(otpUsedList);
    }
}
