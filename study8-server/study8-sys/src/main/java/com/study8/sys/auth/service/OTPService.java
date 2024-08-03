package com.study8.sys.auth.service;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.req.SendOTPReq;
import com.study8.sys.auth.res.SendOTPRes;

import java.util.Locale;

/**
 * OTPService
 * @Date: 2024-07-12
 * @Author: HuyNH
 * @Desc: OTP Service
 */
public interface OTPService {
    SendOTPRes sendOTP(SendOTPReq sendOTPReq, Locale locale)
            throws CoreApplicationException;
}
