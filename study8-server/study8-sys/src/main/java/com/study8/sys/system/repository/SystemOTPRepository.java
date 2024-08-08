package com.study8.sys.system.repository;

import com.study8.sys.system.dto.SystemOTPDto;
import com.study8.sys.system.entity.SystemOTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * SystemOTPRepository
 * @Date: 2024-08-06
 * @Author: HuyNH
 * @Desc: SystemOTP Repository
 */
@Repository
public interface SystemOTPRepository extends JpaRepository<SystemOTP, Long> {
    @Query("SELECT new com.study8.sys.system.dto.SystemOTPDto(so.userId, so.otpType, so.otpCode, so.active, so.sentDate, so.expiryDate) " +
            "FROM SystemOTP so WHERE so.userId = :userId " +
            "AND COALESCE(so.deleted, 0) = 0")
    SystemOTPDto findByUserId(Long userId);
}
