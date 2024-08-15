package com.study8.sys.system.service;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.system.req.SendOTPReq;
import com.study8.sys.system.res.SendOTPRes;
import com.study8.sys.system.dto.SystemOTPDto;
import com.study8.sys.system.entity.SystemOTP;
import com.study8.sys.system.res.VerifyOTPRes;

import java.util.Locale;

/**
 * OTPService
 * @Date: 2024-07-12
 * @Author: HuyNH
 * @Desc: OTP Service
 */
public interface SystemOTPService {
    SendOTPRes sendOTP(SendOTPReq sendOTPReq, Locale locale)
            throws CoreApplicationException;

    SystemOTPDto getByUserId(Long userId);

    SystemOTP updateActive(Long systemOTPId, boolean isVerified);

    VerifyOTPRes verifyOTP(String username, String code, Locale locale)
            throws CoreApplicationException;
}
