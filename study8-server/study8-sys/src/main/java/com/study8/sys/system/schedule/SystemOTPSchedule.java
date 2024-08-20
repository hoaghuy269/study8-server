package com.study8.sys.system.schedule;

import com.study8.sys.system.entity.SystemOTP;
import com.study8.sys.system.service.SystemOTPService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * SystemOTPSchedule
 * @Date: 2024-08-20
 * @Author: HuyNH
 * @Desc: SystemOTP Schedule
 */
@Component
public class SystemOTPSchedule {
    @Autowired
    @Lazy
    private SystemOTPService systemOTPService;

    private static final Logger log = LoggerFactory.getLogger(SystemOTPSchedule.class);

    @Scheduled(fixedRate = 600000)
    public void updateActiveOTPJob() {
        LocalDateTime currentDate = LocalDateTime.now();
        int pageSize = 50;
        int pageNumber = 0;

        Page<SystemOTP> systemOTPPage;
        try {
            do {
                Pageable pageable = PageRequest
                        .of(pageNumber, pageSize);
                systemOTPPage = systemOTPService
                        .findExpiredOTP(currentDate, pageable);
                List<SystemOTP> systemOTPUpdated = systemOTPPage
                        .getContent();
                systemOTPUpdated.forEach(otp -> {
                    otp.setActive(false);
                    otp.setDeleted(1);
                    otp.setDeletedDate(currentDate);
                });
                systemOTPService.saveAllEntityList(systemOTPUpdated);
                pageNumber++;
            } while (systemOTPPage.hasNext());
        } catch (Exception e) {
            log.error("SystemOTPSchedule | updateActiveOTPJob", e);
        }
    }
}
