package com.study8.sys.auth.service.impl;

import com.study8.core.exception.CoreApplicationException;
import com.study8.sys.auth.enumf.SendOTPEnum;
import com.study8.sys.auth.req.SendOTPReq;
import com.study8.sys.auth.res.SendOTPRes;
import com.study8.sys.auth.service.OTPService;
import com.study8.sys.system.dto.SendEmailDto;
import com.study8.sys.system.dto.SendEmailResultDto;
import com.study8.sys.system.enumf.EmailEnum;
import com.study8.sys.system.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Locale;

/**
 * OTPServiceImpl
 * @Date: 2024-07-12
 * @Author: HuyNH
 * @Desc: OTP Service Impl
 */
@Service
public class OTPServiceImpl implements OTPService {
    @Autowired
    private EmailService emailService;

    @Override
    public SendOTPRes sendOTP(SendOTPReq sendOTPReq, Locale locale)
            throws CoreApplicationException {
        SendOTPEnum sendOTPEnum = SendOTPEnum.resolveByValue(sendOTPReq.getType());
        switch (sendOTPEnum) {
            case PHONE_NUMBER -> {
                this.sendPhoneNumberOTP();
            }
            case EMAIL -> {
                this.sendEmailOTP(sendOTPReq, locale);
            }
        }
        return null;
    }

    private void sendEmailOTP(SendOTPReq sendOTPReq,  Locale locale)
            throws CoreApplicationException {
        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setTo(Collections.singletonList(sendOTPReq.getEmail()));
        sendEmailDto.setTemplateCode(EmailEnum.OTP_EMAIL.toString());
        SendEmailResultDto sendEmailResultDto = emailService
                .sendEmailSMTP(sendEmailDto, locale);
    }

    private void sendPhoneNumberOTP() {

    }
}
