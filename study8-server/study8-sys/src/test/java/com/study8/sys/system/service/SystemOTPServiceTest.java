package com.study8.sys.system.service;

import com.study8.sys.system.entity.SystemOTP;
import com.study8.sys.system.repository.SystemOTPRepository;
import com.study8.sys.system.service.impl.SystemOTPServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

/**
 * SystemOTPServiceTest
 * @Date: 2024-07-15
 * @Author: HuyNH
 * @Desc: SystemOTP Service Test
 */
@ExtendWith(MockitoExtension.class)
public class SystemOTPServiceTest {
    @Mock
    private SystemOTPRepository systemOTPRepository;

    @InjectMocks
    private SystemOTPServiceImpl systemOTPService;

    @Test
    public void testUpdateActiveOTPJob() {
        LocalDateTime currentDate = LocalDateTime.now();
        int pageSize = 50;
        int pageNumber = 0;

        SystemOTP otp = new SystemOTP();
        otp.setActive(true);
        otp.setDeleted(0);
        otp.setExpiryDate(currentDate.minusDays(1));

        List<SystemOTP> otpList = Collections.singletonList(otp);
        Page<SystemOTP> otpPage = new PageImpl<>(otpList, PageRequest.of(pageNumber, pageSize), 1);

        when(systemOTPRepository.findExpiredOTP(any(LocalDateTime.class), any(Pageable.class)))
                .thenReturn(otpPage);

        systemOTPService.updateActiveOTPJob();

        verify(systemOTPRepository, times(1)).findExpiredOTP(any(LocalDateTime.class), any(Pageable.class));
        verify(systemOTPRepository, times(1)).saveAll(otpList);

        assertThat(otpList.get(0).getActive()).isFalse();
        assertThat(otpList.get(0).getDeleted()).isEqualTo(1);
        assertThat(otpList.get(0).getDeletedDate()).isCloseTo(currentDate, within(1, ChronoUnit.SECONDS));
    }
}
