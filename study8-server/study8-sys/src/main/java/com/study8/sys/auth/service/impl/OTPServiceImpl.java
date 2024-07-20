package com.study8.sys.auth.service.impl;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.enumf.SendOTPEnum;
import com.study8.sys.auth.req.SendOTPReq;
import com.study8.sys.auth.res.SendOTPRes;
import com.study8.sys.auth.service.OTPService;
import org.springframework.stereotype.Service;

/**
 * OTPServiceImpl
 * @Date: 2024-07-12
 * @Author: HuyNH
 * @Desc: OTP Service Impl
 */
@Service
public class OTPServiceImpl implements OTPService {
    @Override
    public SendOTPRes sendOTP(SendOTPReq sendOTPReq) throws CoreApplicationException {
        SendOTPEnum sendOTPEnum = SendOTPEnum.resolveByValue(sendOTPReq.getType());
        switch (sendOTPEnum) {
            case PHONE_NUMBER -> {
                this.sendPhoneNumberOTP();
            }
            case EMAIL -> {
                this.sendEmailOTP();
            }
        }
        return null;
    }

    private void sendEmailOTP() {

    }

    private void sendPhoneNumberOTP() {

    }
}
