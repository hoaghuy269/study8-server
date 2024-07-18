package com.study8.sys.auth.rest.v1.impl;

import com.study8.core.res.CoreApiRes;
import com.study8.core.util.CoreLanguageUtils;
import com.study8.sys.auth.constant.AuthExceptionConstant;
import com.study8.sys.auth.dto.AppUserDto;
import com.study8.sys.auth.req.LoginReq;
import com.study8.sys.auth.req.RegisterReq;
import com.study8.sys.auth.req.SendOTPReq;
import com.study8.sys.auth.res.LoginRes;
import com.study8.sys.auth.res.RegisterRes;
import com.study8.sys.auth.res.SendOTPRes;
import com.study8.sys.auth.rest.v1.AuthRest;
import com.study8.sys.auth.services.AppUserService;
import com.study8.sys.auth.services.OTPService;
import com.study8.sys.util.BindingResultUtils;
import com.study8.sys.util.JwtUtils;
import com.study8.sys.util.ResourceBundleUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * AuthRestImpl
 * @Date: 2024-06-17
 * @Author: HuyNH
 * @Desc: Auth Rest Impl
 */
@RestController
@Slf4j
public class AuthRestImpl implements AuthRest {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private OTPService otpService;

    @Override
    public CoreApiRes<LoginRes> login(LoginReq loginReq,
            BindingResult bindingResult, HttpServletRequest request,
            HttpServletResponse response) {
        Locale locale = CoreLanguageUtils.getLanguageFromHeader(request);
        try {
            BindingResultUtils.handleBindingResult(bindingResult, locale);
            //Authentication
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginReq.getUsername(), loginReq.getPassword()));
            SecurityContextHolder.getContext()
                    .setAuthentication(authentication);
            //JWT
            String jwt = jwtUtils.generateJwtToken(authentication);
            LoginRes res = new LoginRes();
            if (StringUtils.isNotEmpty(jwt)) {
                res.setToken(jwt);
            }
            return CoreApiRes.handleSuccess(res, locale);
        } catch (BadCredentialsException e) {
            log.error("AuthService | login", e);
            return CoreApiRes.handleError(ResourceBundleUtils
                    .getMessage(AuthExceptionConstant.EXCEPTION_AUTH_ACCOUNT_NOT_VALID,
                            locale));
        } catch (Exception e) {
            log.error("AuthRestImpl | login", e);
            return CoreApiRes.handleError(e.getMessage());
        }
    }

    @Override
    public CoreApiRes<RegisterRes> register(RegisterReq registerReq,
                                         BindingResult bindingResult, HttpServletRequest request,
                                         HttpServletResponse response) {
        Locale locale = CoreLanguageUtils.getLanguageFromHeader(request);
        try {
            BindingResultUtils.handleBindingResult(bindingResult, locale);
            RegisterRes res = new RegisterRes();
            AppUserDto appUserDto = appUserService.register(registerReq, locale);
            if (ObjectUtils.isNotEmpty(appUserDto)) {
                res.setUserCode(appUserDto.getCode());
            }
            return CoreApiRes.handleSuccess(res, locale);
        } catch (Exception e) {
            log.error("AuthRestImpl | register", e);
            return CoreApiRes.handleError(e.getMessage());
        }
    }

    @Override
    public CoreApiRes<SendOTPRes> sendOTP(SendOTPReq sendOTPReq, BindingResult bindingResult,
                                          HttpServletRequest request, HttpServletResponse response) {
        Locale locale = CoreLanguageUtils.getLanguageFromHeader(request);
        try {
            BindingResultUtils.handleBindingResult(bindingResult, locale);
            SendOTPRes res = otpService.sendOTP(sendOTPReq);
            return CoreApiRes.handleSuccess(res, locale);
        } catch (Exception e) {
            log.error("AuthRestImpl | sendOTP", e);
            return CoreApiRes.handleError(e.getMessage());
        }
    }
}
