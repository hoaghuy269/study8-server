package com.study8.sys.auth.services;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.req.SendOTPReq;
import com.study8.sys.auth.res.SendOTPRes;

/**
 * OTPService
 * @Date: 2024-07-12
 * @Author: HuyNH
 * @Desc: OTP Service
 */
public interface OTPService {
    SendOTPRes sendOTP(SendOTPReq sendOTPReq) throws CoreApplicationException;
}
