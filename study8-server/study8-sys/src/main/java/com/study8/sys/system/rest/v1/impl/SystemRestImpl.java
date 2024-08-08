package com.study8.sys.system.rest.v1.impl;

import com.study8.core.res.CoreApiRes;
import com.study8.core.util.CoreLanguageUtils;
import com.study8.sys.auth.req.SendOTPReq;
import com.study8.sys.auth.res.SendOTPRes;
import com.study8.sys.system.rest.v1.SystemRest;
import com.study8.sys.system.service.SystemOTPService;
import com.study8.sys.util.BindingResultUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * SystemRestImpl
 * @Date: 2024-08-08
 * @Author: HuyNH
 * @Desc: SystemRest Impl
 */
@RestController
@Slf4j
public class SystemRestImpl implements SystemRest {
    @Autowired
    private SystemOTPService systemOtpService;

    @Override
    public CoreApiRes<SendOTPRes> sendOTP(SendOTPReq sendOTPReq, BindingResult bindingResult,
                                          HttpServletRequest request, HttpServletResponse response) {
        Locale locale = CoreLanguageUtils.getLanguageFromHeader(request);
        try {
            BindingResultUtils.handleBindingResult(bindingResult, locale);
            SendOTPRes res = systemOtpService.sendOTP(sendOTPReq, locale);
            return CoreApiRes.handleSuccess(res, locale);
        } catch (Exception e) {
            log.error("AuthRestImpl | sendOTP", e);
            return CoreApiRes.handleError(e.getMessage());
        }
    }

    @Override
    public CoreApiRes<SendOTPRes> verifyOTP(String username, String code, HttpServletRequest request, HttpServletResponse response) {
        return null;
    }
}
